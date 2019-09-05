package com.example.rbac.service.impl;

import com.example.rbac.entity.User;
import com.example.rbac.mapper.UserMapper;
import com.example.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByName(String name) {
//        Map map = userMapper.findUserByName(name);
//        return new User();
        return userMapper.findUserByName(name);
    }

    @Override
    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }
}
