/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.common.controller;

import com.common.mapper.JsonMapper;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器支持类
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;


//    /**
//     * 登录认证异常
//     */
//    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
//    public ModelAndView authenticationException(HttpServletRequest request, HttpServletResponse response) {
//        if (isAjaxRequest(request)) {
//            // 输出JSON
//            Map<String, Object> map = Maps.newHashMap();
//            map.put("code", "-999");
//            map.put("message", "未登录");
//            writeJson(map, response);
//            return null;
//        } else {
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.setViewName("modules/sys/login");
//            return modelAndView;
//        }
//    }

    /**
     * 权限异常
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public ModelAndView authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if (isAjaxRequest(request)) {
            // 输出JSON
            Map<String, Object> map = Maps.newHashMap();
            map.put("code", "-998");
            map.put("message", "没有操作权限");
            writeJson(map, response);
            return null;
        } else {
            ModelAndView modelAndView = new ModelAndView("/common/error/403");
            return modelAndView;
        }
    }

    /**
     * 输出JSON
     *
     * @param response
     * @author SHANHY
     * @create 2017年4月4日
     */
    private void writeJson(Map<String, Object> map, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(JsonMapper.toJsonString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是 ajax 请求
     *
     * @param request
     * @return
     */
    private static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

//    @RequestMapping("/common/error/403")
//    public ModelAndView error403(ModelAndView modelAndView){
//        modelAndView.setViewName("/common/error/403");
//        return modelAndView;
//    }
//
//    @RequestMapping("/common/error/404")
//    public ModelAndView error404(ModelAndView modelAndView){
//        modelAndView.setViewName("/common/error/404");
//        return modelAndView;
//    }
}
