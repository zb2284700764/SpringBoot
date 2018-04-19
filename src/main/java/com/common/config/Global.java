/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.common.config;

import com.common.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * 全局配置类
 */
@Configuration
public class Global {


    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();
    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader;

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    static {
        System.out.println(System.getProperty("user.home") + File.separator + "application.properties");
        loader = new PropertiesLoader(System.getProperty("user.home") + File.separator + "application.properties");
//        loader = new PropertiesLoader("application.properties");
    }


    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }


    /**
     * 获取 session 前缀
     */
    public static String getSessionPrefix() {
        return getConfig("redis.shiro.session.prefix");
    }

    /**
     * 获取 session 过期时间(秒)
     */
    public static long getSessionExpireTime() {
        return Long.parseLong(getConfig("redis.shiro.session.expireTime"));
    }

    /**
     * 获取 cache 前缀
     */
    public static String getCachePrefix() {
        return getConfig("redis.shiro.cache.prefix");
    }

    /**
     * 获取 cache 过期时间(秒)
     */
    public static long getCacheExpireTime() {
        return Long.parseLong(getConfig("redis.shiro.cache.expireTime"));
    }

}
