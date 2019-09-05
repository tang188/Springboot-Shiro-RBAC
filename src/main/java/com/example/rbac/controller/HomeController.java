package com.example.rbac.controller;

import com.example.rbac.module.Response;
import com.example.rbac.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private LoginService loginService;

    @RequestMapping({"/", "/index"})
    public String index(){
        return "/index";
    }

    @RequestMapping("/403")
    public String unAuthorized(){
        return "/user/403";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Map<String, Object> map, HttpServletRequest request){
        loginService.logout();
        return "/user/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login1(Map<String, Object> map, HttpServletRequest request){

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        //模拟前台传进角色
        HttpSession session = request.getSession();
        session.setAttribute("role","admin");
        Response loginResult = loginService.login(userName,password);
        if("200".equals(loginResult.getResponseCode()))
        {
            return "/index";
        }
        else {
            map.put("msg",loginResult.getMsg());
            map.put("userName",userName);
            return "/user/login";
        }
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        loginService.logout();
        return "/user/login";
    }
}
