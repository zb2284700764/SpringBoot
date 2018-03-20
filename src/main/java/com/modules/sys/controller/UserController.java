package com.modules.sys.controller;

import com.common.controller.BaseController;
import com.modules.sys.entity.User;
import com.modules.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("${adminPath}/sys/user")
public class UserController extends BaseController {

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
        modelAndView.setViewName("modules/sys/hello");
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
        modelAndView.setViewName("modules/sys/hello2");
        LOGGER.info("使用logback测试日志记录2");
        return modelAndView;
    }


    @RequestMapping("/index3")
    public ModelAndView index3(@PathVariable String username, @PathVariable String password, ModelAndView modelAndView) {
        modelAndView.addObject("username", username);
        modelAndView.addObject("password", password);
        modelAndView.setViewName("modules/sys/hello2");
        LOGGER.info("使用logback测试日志记录2");
        return modelAndView;
    }











































    /**
     * 增加用户
     * @param user
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping("/save")
    public String save(User user) {

        System.out.println("save user");

//		SystemService.entryptPassword(user.getPassword());
        System.out.println("增加成功");

        return "redirect:" + adminPath + "/sys/user/findAllUser";
    }

    /**
     * 跳转到增加用户页面
     * @date 2017年9月19日 下午2:20:59
     */
    @RequestMapping("/gotoUserForm")
    public ModelAndView gotoUserForm(ModelAndView modelAndView) {

        System.out.println("gotoUserForm");

        List<User> userList = userService.findAllUser();
        modelAndView.setViewName("modules/sys/userForm");
        return modelAndView;
    }

    /**
     * 查询所有用户
     * @param modelAndView
     * @date 2017年9月18日 下午5:20:40
     */
    @RequestMapping("/findAllUser")
    public ModelAndView findAllUser(ModelAndView modelAndView) {
        List<User> userList = userService.findAllUser();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("modules/sys/userList");
        return modelAndView;
    }
}
