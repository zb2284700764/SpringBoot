package com.common.core.filters;

import com.common.core.shiro.SystemAuthorizingRealm;
import com.google.common.collect.Lists;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 登录踢出 filter 用这个 filter 需要在 ShiroConfiguration 类中加入 filter
 * 然后在拦截的 url 上加入这个 filter
 */
public class KickoutSessionFilter extends AccessControlFilter{
    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            // 返回 true 表示该拦截器已经处理完成，还需要后面的拦截器接着处理
            return true;
        }

        Session session = subject.getSession();
        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
        Serializable sessionId = session.getId();

        Deque<Serializable> deque;
        synchronized (this) {
            deque = cache.get(principal.getLoginName());
            if(deque == null) {
                deque = Lists.newLinkedList();
            }
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的 sessionId 数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        // 将回话队列放入缓存
        cache.put(principal.getLoginName(), deque);

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout")) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
            }
//            saveRequest(request);
//            WebUtils.issueRedirect(request, response, getLoginUrl());
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }

        return true;
    }
}
