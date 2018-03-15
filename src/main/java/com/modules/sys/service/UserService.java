package com.modules.sys.service;

import com.modules.sys.dao.UserMapper;
import com.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    public int addUser(User user) {
        return userMapper.insert(user);
    }

    public List<User> findAllUser(int pageNum, int pageSize) {
        return userMapper.selectAll();
    }

}
