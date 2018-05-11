package com.modules.sys.controller;

import com.common.controller.BaseController;
import com.modules.sys.entity.User;
import com.modules.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("${adminPath}/sys/user")
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/ajaxFindAllUser")
    @RequiresPermissions("sys:user:view")
    public Object ajaxFindAllUser() {
        List<User> userList = userService.findAllUser();

        return userList;
    }

    /**
     * 增加用户
     *
     * @param user
     * @return
     */
    @RequiresUser
    @RequiresPermissions("sys:user:add")
    @RequestMapping("/save")
    public String save(@ModelAttribute(value = "user") User user) {

        userService.save(user);

        LOGGER.info("UserController -> save -> 增加成功");
        return "redirect:" + adminPath + "/sys/user/findAllUser";
    }

    /**
     * 跳转到增加用户页面
     *
     * @date 2017年9月19日 下午2:20:59
     */
    @RequiresPermissions("sys:user:add")
    @RequestMapping("/gotoUserForm")
    public ModelAndView gotoUserForm(ModelAndView modelAndView) {

        // 对应 userForm.html 页面中 saveForm 下的表单对象
        modelAndView.addObject("user", new User());

        modelAndView.setViewName("modules/sys/userForm");
        LOGGER.info("UserController -> gotoUserForm -> 增加成功");
        return modelAndView;
    }

    /**
     * 查询所有用户
     *
     * @param modelAndView
     * @date 2017年9月18日 下午5:20:40
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping("/findAllUser")
    public ModelAndView findAllUser(ModelAndView modelAndView) {
        List<User> userList = userService.findAllUser();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("modules/sys/userList");
        return modelAndView;
    }

    /**
     * 发送消息到 Redis 的消息队列中
     * @return
     */
    @RequestMapping("/sendMessage")
    public boolean sendMessage() {

        redisTemplate.convertAndSend("testTopic", "测试消息");

        return true;
    }
}
