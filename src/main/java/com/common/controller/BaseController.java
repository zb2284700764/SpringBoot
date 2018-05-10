package com.common.controller;

import com.common.core.shiro.SystemAuthorizingRealm;
import com.common.mapper.JsonMapper;
import com.google.common.collect.Maps;
import com.modules.sys.entity.Menu;
import com.modules.sys.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 控制器父类
 */
public abstract class BaseController {

    @Autowired
    protected RedisTemplate redisTemplate;
    @Autowired
    protected MenuService menuService;

    /**
     * 日志对象
     */
    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;


    /**
     * 查询菜单
     * @param modelAndView 在当前 controller 里面只存放菜单数据
     * @return 菜单数据
     */
    @ModelAttribute
    public ModelAndView initMenu(ModelAndView modelAndView) {
        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            List<Menu> menuList = menuService.findMenuByUserId(principal.getId());
            if (menuList != null) {
                modelAndView.addObject("menuList",menuList);
            }
        }
        return modelAndView;
    }

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
            return new ModelAndView("/common/error/403");
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
     * @param request 请求
     * @return 判断结果（true/false）
     */
    private static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        return requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest");
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
