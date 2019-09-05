package com.example.rbac.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @RequestMapping("/hello")
    public String sayHello(){
        return "hello!";
    }

}
