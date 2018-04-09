package com.modules.sys.service;

import com.common.service.CrudService;
import com.modules.sys.dao.UserDao;
import com.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends CrudService<UserDao, User> {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据登录名获取用户信息
     * @Title getByLoginName
     * @require
     * @param loginName
     * @return
     * @throws
     * @author zhoubin
     * @date 2017年7月6日 下午4:55:50
     * @history
     */
    public User getByLoginName(String loginName) {

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

        List<User> userList = dao.findAllUser();

        for (User user : userList) {
            redisTemplate.opsForValue().set(user.getId(),user.getLoginName());
        }



        return userList;
    }

}
