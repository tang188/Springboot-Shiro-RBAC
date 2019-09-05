package com.example.rbac.service;

import com.example.rbac.module.Response;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    Response login(String userName, String pwd);
    void logout();
}
