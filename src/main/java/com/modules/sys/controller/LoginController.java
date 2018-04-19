package com.modules.sys.controller;

import com.common.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登录 Controller
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 登录
     *
     * @date 2017年7月10日 下午4:27:19
     */
    @RequestMapping(value = "${adminPath}/login")
    public String login() {

//		UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
//		subject.login(token); // 模拟登录
//		subject.logout(); // 模拟退出

        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        subject.getSession().getId();
        // 登录认证通过
        if (principal != null) {
            // 转发到 UserController 查询用户列表
            return "redirect:" + adminPath + "/sys/user/findAllUser";
        } else {
            return "modules/sys/login";
        }
    }

    /**
     * 退出登录
     *
     * @date 2017年7月10日 下午4:27:19
     */
    @RequestMapping(value = "${adminPath}/loginOut")
    public String loginOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "modules/sys/login";
    }


    /**
     * 默认页面
     *
     * @date 2017年7月6日 上午10:21:20
     */
    @RequestMapping("index")
    public String defaultIndex() {

        return "modules/index";
    }
}

