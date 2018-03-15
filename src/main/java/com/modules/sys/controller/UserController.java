package com.modules.sys.controller;

import com.modules.sys.entity.User;
import com.modules.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * post 方法，通过 @RequestParam 绑定参数值
     *
     * @param name
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/index1", method = RequestMethod.POST)
    public ModelAndView index1(@RequestParam("name") String name, ModelAndView modelAndView) {
        modelAndView.addObject("name", name);
        modelAndView.setViewName("hello");
        LOGGER.info("使用logback测试日志记录1");
        return modelAndView;
    }

    /**
     * url 中的参数通过 @PathVariable 在方法参数中绑定值，请求参数中不用指定参数名，只需要顺序对应
     * 请求的 url 为 http://localhost:8080/index2/张三/123456
     *
     * @param username
     * @param password
     * @param modelAndView
     * @return
     */
    @RequestMapping("/index2/{username}/{password}")
    public ModelAndView index2(@PathVariable String username, @PathVariable String password, ModelAndView modelAndView) {
        modelAndView.addObject("username", username);
        modelAndView.addObject("password", password);
        modelAndView.setViewName("hello2");
        LOGGER.info("使用logback测试日志记录2");
        return modelAndView;
    }


    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        return userService.findAllUser(pageNum,pageSize);
    }
}
