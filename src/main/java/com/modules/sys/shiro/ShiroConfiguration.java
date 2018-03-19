package com.modules.sys.shiro;


import com.google.common.collect.Maps;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * Shiro 配置，替代 XML 配置方式
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        System.out.println("--------------shiroFilter 已加载----------------");


        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面
        shiroFilterFactoryBean.setLoginUrl("/a/login");

        // 登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此
        shiroFilterFactoryBean.setSuccessUrl("/a?login");

        // 没有权限默认跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/a/defaultIndex");

        // 权限过滤的页面
        LinkedHashMap filterChainDefinitionMap = Maps.newLinkedHashMap();
        filterChainDefinitionMap.put("/a/sys/user/index1", "anon"); // anon 表示可以匿名访问
        filterChainDefinitionMap.put("/a/sys/user/index2", "anon"); // anon 表示可以匿名访问
        filterChainDefinitionMap.put("/a/sys/user/index3", "authc"); // authc 表示需要认证才可以访问

        filterChainDefinitionMap.put("/static/**", "anon"); // anon 表示可以匿名访问
        filterChainDefinitionMap.put("/a/login", "authc"); // authc 表示需要认证才可以访问
        filterChainDefinitionMap.put("/logout", "logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("systemAuthorizingRealm") SystemAuthorizingRealm systemAuthorizingRealm) {
        System.out.println("--------------shiro securityManager 已经加载----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(systemAuthorizingRealm);
        return manager;
    }


}
