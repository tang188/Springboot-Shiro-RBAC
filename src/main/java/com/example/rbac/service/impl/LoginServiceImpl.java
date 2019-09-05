package com.example.rbac.service.impl;

import com.example.rbac.module.Response;
import com.example.rbac.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService  {

    @Override
    public Response<String> login(String userName, String pwd) {
        Response res = new Response();

        String msg = "success";
        if(userName==null || userName.isEmpty())
        {
            res.setResponseCode("403");
            msg = "账号或密码为空";
            res.setMsg(msg);
            return res;
        }
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
//        session.setAttribute("role","admin");
        UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
        try {
            currentUser.login(token);
            session.setAttribute("userName", userName);
            res.setResponseCode("200");
            res.setMsg(msg);
            return res;
        } catch (UnknownAccountException e) {
            res.setResponseCode("403");
            msg = "UnknownAccountException -- > 账号不存在：";
        } catch (IncorrectCredentialsException e) {
            res.setResponseCode("403");
            msg = "IncorrectCredentialsException -- > 密码不正确：";
        } catch (AuthenticationException e) {
            res.setResponseCode("403");
            msg = "用户验证失败";
        }
        res.setMsg(msg);
        return res;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
