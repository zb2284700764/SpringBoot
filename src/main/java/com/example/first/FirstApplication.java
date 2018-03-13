package com.example.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@SpringBootApplication
public class FirstApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

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

}