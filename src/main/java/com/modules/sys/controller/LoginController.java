package com.modules.sys.controller;

import com.common.controller.BaseController;
import com.google.common.collect.Lists;
import com.modules.sys.entity.Menu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 后台登录 Controller
 */
@Controller
public class LoginController extends BaseController {

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
            // 查询菜单

            List<Menu> menuList = Lists.newArrayList();
            Menu menu = new Menu();
            menu.setName("用户管理");
            menuList.add(menu);
            modelAndView.addObject("menuList", menuList);
            modelAndView.setViewName("modules/index");

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
    @RequestMapping(value = "/")
    public String login() {

        return "redirect:" + adminPath + "/login";
    }

    /**
     * 默认页面
     *
     * @date 2017年7月6日 上午10:21:20
     */
    @RequestMapping("${adminPath}/index")
    public String defaultIndex() {

        return "modules/index";
    }

}

