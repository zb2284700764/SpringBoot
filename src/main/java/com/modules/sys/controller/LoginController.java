package com.modules.sys.controller;

import com.common.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录Controller
 */
@Controller
public class LoginController extends BaseController {


	/**
	 * 登录
	 * @param model
	 * @date 2017年7月10日 下午4:27:19
	 */
	@RequestMapping(value = "${adminPath}/login")
	public String login(Model model) {
		
//		UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
		Subject subject = SecurityUtils.getSubject();
//		subject.login(token); // 模拟登录
//		subject.logout(); // 模拟退出

		Object principal = subject.getPrincipal();
		subject.getSession().getId();
		// 登录认证通过
		if(principal != null) {
			// 转发到 UserController 查询用户列表
			return "redirect:" + adminPath + "/sys/user/findAllUser";
		} else {
			return "sys/login";
		}
	}


	/**
	 * 退出登录
	 * @param loginName
	 * @param password
	 * @param model
	 * @date 2017年7月10日 下午4:27:19
	 */
	@RequestMapping(value = "${adminPath}/loginOut")
	public String loginOut(String loginName, String password, Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "sys/login";
	}


	/**
	 * 输入项目名称之后默认跳转到此方法
	 * @date 2017年7月6日 上午10:19:15
	 */
	@RequestMapping("${adminPath}")
	public String login() {
		System.out.println("a");
		
		return "redirect:" + adminPath + "/login";
	}



	/**
	 * 默认页面
	 * @date 2017年7月6日 上午10:21:20
	 */
	@RequestMapping("${adminPath}/defaultIndex")
	public String defaultIndex(){

		return "sys/defaultIndex";
	}
}

