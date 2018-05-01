package com.common.core.shiro;

import com.common.core.filters.KickoutSessionFilter;
import com.common.core.shiro.cache.RedisCacheManager;
import com.common.core.shiro.session.RedisSessionDao;
import com.google.common.collect.Maps;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Shiro 配置，替代 XML 配置方式
 */
@Configuration
public class ShiroConfiguration {


//    @Qualifier("formAuthenticationFilter")
//    FormAuthenticationFilter formAuthenticationFilter;

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
        return sessionIdGenerator;
    }


    @Bean(name = "redisSessionDao")
    public RedisSessionDao redisSessionDao() {
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        redisSessionDao.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
        redisSessionDao.setCacheManager(redisCacheManager());
        return redisSessionDao;
    }


    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }


    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDao());
        // session 失效时间(毫秒)
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        sessionManager.setSessionValidationInterval(120 * 1000);
        // 定时检查 session 失效
        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // cookie 生效时间 30 天，单位(秒)
        cookie.setMaxAge(30 * 24 * 60 * 60);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // 设置 rememberMe cookie 加密的密匙，默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("DoHkenUvfszBE0/Z017AHw=="));
        return cookieRememberMeManager;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("systemAuthorizingRealm") SystemAuthorizingRealm systemAuthorizingRealm) {
        System.out.println("--------------shiro securityManager 已经加载----------------");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm 为自定义实现 AuthorizingRealm 的 realm
        systemAuthorizingRealm.setCachingEnabled(true);
//        systemAuthorizingRealm.setAuthenticationCachingEnabled(true);
//        systemAuthorizingRealm.setAuthenticationCacheName("authenticationCache");
        systemAuthorizingRealm.setAuthorizationCachingEnabled(true);
        systemAuthorizingRealm.setAuthorizationCacheName("authorizationCache");
        securityManager.setRealm(systemAuthorizingRealm);

        // 设置 shiro 会话管理器
        securityManager.setSessionManager(sessionManager());

        // 设置 shiro 缓存管理器
        securityManager.setCacheManager(redisCacheManager());

        // 设置记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }


    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
     * AOP式方法级权限检查
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由 Advisor 决定对哪些类的方法进行AOP代理
     * AOP式方法级权限检查
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAOP = new DefaultAdvisorAutoProxyCreator();
        defaultAOP.setProxyTargetClass(true);
        return defaultAOP;
    }


    /**
     * 自定义实现 FormAuthenticationFilter 这个 Filter 之后，需要加入让自定义的 Filter 在 Shiro 的 Filter 加载之后再加载
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(FormAuthenticationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        System.out.println("--------------shiroFilter 已加载----------------");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面
        shiroFilterFactoryBean.setLoginUrl("/a/login");

        // 登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此
        shiroFilterFactoryBean.setSuccessUrl("/a/index");
        Map<String,Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new FormAuthenticationFilter());
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        kickoutSessionFilter.setMaxSession(1);
        kickoutSessionFilter.setSessionManager(sessionManager());
        kickoutSessionFilter.setCacheManager(redisCacheManager());

        filters.put("kickout", kickoutSessionFilter);
        shiroFilterFactoryBean.setFilters(filters);

        // 没有权限默认跳转的页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/a/defaultIndex");

        // 权限过滤的页面
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();

        filterChainDefinitionMap.put("/static/**", "anon"); // anon 表示可以匿名访问
        filterChainDefinitionMap.put("/a/login", "authc"); // authc 表示需要认证才可以访问
        filterChainDefinitionMap.put("/a/logout", "logout");
        filterChainDefinitionMap.put("/a/**", "user,kickout"); // user 用户拦截器 用户已经身份验证/记住我登录的都可
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }



    /**
     * 异常拦截并跳转到对应的界面
     *
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();

        // key 为异常类，value 为直接对应的页面
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/common/error/403");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }


}
