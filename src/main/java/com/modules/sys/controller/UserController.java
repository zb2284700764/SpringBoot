package com.modules.sys.controller;

import com.common.controller.BaseController;
import com.modules.sys.entity.User;
import com.modules.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("${adminPath}/sys/user")
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 增加用户
     *
     * @param user
     * @return
     */
//    @RequiresPermissions("add")
    @RequestMapping("/save")
    public String save(@ModelAttribute(value = "user") User user) {

        System.out.println("save user");

        userService.save(user);

        System.out.println("增加成功");

        return "redirect:" + adminPath + "/sys/user/findAllUser";
    }

    /**
     * 跳转到增加用户页面
     *
     * @date 2017年9月19日 下午2:20:59
     */
    @RequestMapping("/gotoUserForm")
    public ModelAndView gotoUserForm(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());

        modelAndView.setViewName("sys/userForm");
        return modelAndView;
    }

    /**
     * 查询所有用户
     *
     * @param modelAndView
     * @date 2017年9月18日 下午5:20:40
     */
    @RequestMapping("/findAllUser")
    public ModelAndView findAllUser(ModelAndView modelAndView) {
        List<User> userList = userService.findAllUser();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("sys/userList");
        return modelAndView;
    }
}
