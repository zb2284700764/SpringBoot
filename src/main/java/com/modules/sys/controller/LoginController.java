package com.modules.sys.controller;

import com.common.config.Global;
import com.common.controller.BaseController;
import com.common.core.shiro.SystemAuthorizingRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * 后台登录 Controller
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "${adminPath}/login")
    public ModelAndView login(ModelAndView modelAndView) {

        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        subject.getSession().getId();
        // 登录认证通过
        if (principal != null) {

        } else {
            modelAndView.setViewName("modules/sys/login");
        }
        // 跳转到首页
        return modelAndView;
    }

    /**
     * 退出登录之后会跳转到此方法
     *
     * @return
     */
    @RequestMapping(value = "/a/logout")
    public String logout() {

        // 先清除这个用户的 userCache
        Subject subject = SecurityUtils.getSubject();
        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
        if (principal != null) {
            Set<String> sets = redisTemplate.keys(Global.userCachePrefix() + principal.getId() + "*");
            for (String set : sets) {
                redisTemplate.delete(set);
            }
        }
        // 删除登录踢出的标识
        redisTemplate.delete("shiro-cache:shiro-kickout-session:" + principal.getLoginName());

        subject.logout();

        return "redirect:" + adminPath + "/login";
    }

    /**
     * 默认页面
     *
     * @date 2017年7月6日 上午10:21:20
     */
    @RequiresPermissions("sys:index:view")
    @RequestMapping("${adminPath}/index")
    public ModelAndView defaultIndex(ModelAndView modelAndView) {
        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) SecurityUtils.getSubject().getPrincipal();
        modelAndView.addObject("user", principal);
        modelAndView.setViewName("modules/index");
        return modelAndView;
    }

}

