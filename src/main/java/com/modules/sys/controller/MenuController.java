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

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);



    @RequestMapping("/findMenuByUser")
    public List<Menu> findMenuByUser(String userId) {
        System.out.println("进来查询菜单");
        List<Menu> menuList = Lists.newArrayList();
        menuList.add(new Menu());

        return menuList;
    }
}
