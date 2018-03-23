package com.modules.sys.service;

import com.common.service.CrudService;
import com.modules.sys.dao.MenuDao;
import com.modules.sys.dao.UserDao;
import com.modules.sys.entity.Menu;
import com.modules.sys.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService extends CrudService<MenuDao, Menu> {


    /**
     * 根据 userId 查询该用户对应的菜单(权限)
     * @param userId
     * @return
     */
    public List<Menu> findMenuByUserId(String userId){

        return dao.findMenuByUserId(userId);
    }
}
