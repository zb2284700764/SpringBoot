package com.modules.demo.controller;

import com.common.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("${adminPath}/demo/test")
public class TestController extends BaseController {




    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView) {

        modelAndView.setViewName("modules/demo/demo");
        return modelAndView;
    }
}
