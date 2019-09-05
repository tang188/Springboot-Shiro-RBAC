package com.example.rbac.service;

import com.example.rbac.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findUserByName(String name);
    User findUserById(String id);

}
