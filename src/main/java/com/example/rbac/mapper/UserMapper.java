package com.example.rbac.mapper;

import com.example.rbac.entity.User;
import com.example.rbac.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Component
public interface UserMapper {

    public User findUserByName(String name) ;

//    public Map findUserByName(String name) ;

    public User findUserById(String id);
}
