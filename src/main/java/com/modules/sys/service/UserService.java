package com.modules.sys.service;

import com.common.service.CrudService;
import com.modules.sys.dao.UserDao;
import com.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends CrudService<UserDao, User> {


    /**
     * 通过登录名获取用户信息
     * @Title getUserByLoginName
     * @require
     * @param loginName 登录名
     * @return
     * @throws
     * @author zhoubin
     * @date 2017年6月30日 下午4:12:33
     * @history
     */
    public User getUserByLoginName(String loginName) {

        return dao.getUserByLoginName(loginName);
    }

    /**
     * 查询所有用户
     * @Title findAllUser
     * @require
     * @return
     * @throws
     * @author zhoubin
     * @date 2017年9月18日 下午5:26:30
     * @history
     */
    public List<User> findAllUser(){
        return dao.findAllUser();
    }

}
