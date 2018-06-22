package com.modules.sys.controller;

import com.common.controller.BaseController;
import com.google.common.collect.Lists;
import com.modules.sys.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${adminPath}/sys/menu")
public class MenuController extends BaseController {


    @RequestMapping("/list")
    public String list(String userId) {

        return null;
    }
}
