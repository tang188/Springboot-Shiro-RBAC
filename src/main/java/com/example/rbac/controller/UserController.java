package com.example.rbac.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userList")
    @RequiresPermissions("user:view")
    public String userInfo(){
        return "/user/userList";
    }

    @RequestMapping("/userAdd")
//    @RequiresRoles(value = {"default-role","admin"}, logical = Logical.OR)
    @RequiresPermissions("user:add")
    public String userAdd(){
        return "/user/userAdd";
    }
}
