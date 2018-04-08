package com.common.core.shiro;

import com.google.common.collect.Maps;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * Shiro 配置，替代 XML 配置方式
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        System.out.println("--------------shiroFilter 已加载----------------");


        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面
        shiroFilterFactoryBean.setLoginUrl("/a/login");

        // 登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此
        shiroFilterFactoryBean.setSuccessUrl("/a?login");

        // 没有权限默认跳转的页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/a/defaultIndex");

        // 权限过滤的页面
        LinkedHashMap filterChainDefinitionMap = Maps.newLinkedHashMap();

        filterChainDefinitionMap.put("/static/**", "anon"); // anon 表示可以匿名访问
//        filterChainDefinitionMap.put("/a/**", "user"); // user 用户拦截器 用户已经身份验证/记住我登录的都可
//        filterChainDefinitionMap.put("/a/loginOut", "logout");
        filterChainDefinitionMap.put("/a/**", "authc"); // authc 表示需要认证才可以访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("systemAuthorizingRealm") SystemAuthorizingRealm systemAuthorizingRealm) {
        System.out.println("--------------shiro securityManager 已经加载----------------");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm 为自定义实现 AuthorizingRealm 的 realm
        securityManager.setRealm(systemAuthorizingRealm);

        // 设置 shiro 缓存管理器
//        securityManager.setCacheManager(null);

        // 设置 shiro 会话管理器
//        securityManager.setSessionManager(null);



        return securityManager;
    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
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
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }


    /**
     * 异常拦截并跳转到对应的界面
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();

        // key 为异常类，value 为直接对应的页面
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/sys/defaultIndex");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }
}
