package com.example.rbac.config;

import com.example.rbac.entity.Permission;
import com.example.rbac.entity.SysRole;
import com.example.rbac.entity.User;
import com.example.rbac.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyRealm  extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    UserService userService;
    /**
     * 授权：角色，权限等
     * 采用缓存：https://www.cnblogs.com/zfding/p/8536480.html
     * @Description:
     * @Param: [principalCollection]
     * @return: org.apache.shiro.authz.AuthorizationInfo
     * @Author: tangzhihong
     * @Date: 2019/9/3 14:58
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("---------->>权限配置：授权->MyRealm.doGetAuthorizationInfo()");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //前台默认传个角色过来
        String loginRole = (String) session.getAttribute("role");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();

        //添加用户组权限
//        List<UserGroup> groups = getUserGroup(user);
//        List<Permission> gPermissions = getPermissionsByGroup(groups);
//        gPermissions.forEach(permission -> simpleAuthorizationInfo.addStringPermission(permission.getPermission()));

        for(SysRole role: Optional.ofNullable(user.getRoleList()).orElse(new ArrayList<SysRole>())){
            //session中单一角色，保持RBAC2中的会话约束DSD
            if (loginRole.equals(role.getRoleName())){
                simpleAuthorizationInfo.addRole(role.getRoleId());
                //可以获取角色继承的所有权限
//                List<Permission> permissions = getPermissionByRole(loginRole);
//                permissions.forEach(permission -> {
//                    simpleAuthorizationInfo.addStringPermission(permission.getPermission());
//                });

                for (Permission permission : role.getPermissions()) {
                    simpleAuthorizationInfo.addStringPermission(permission.getPermission());
                }
            }
        }
        if (simpleAuthorizationInfo.getRoles() == null || simpleAuthorizationInfo.getRoles().isEmpty()){
            simpleAuthorizationInfo.addRole("default-role");
//            simpleAuthorizationInfo.addStringPermission("user:view");
            simpleAuthorizationInfo.addStringPermission("default:default");
        }
        return simpleAuthorizationInfo;
    }

    /**
     * @Description: 验证,登录
     * @Param: [authenticationToken]
     * @return: org.apache.shiro.authc.AuthenticationInfo
     * @Author: tangzhihong
     * @Date: 2019/9/3 16:09
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---------->>权限配置：验证->MyRealm.doGetAuthenticationInfo()");
        String name = (String) authenticationToken.getPrincipal();
        User user = userService.findUserByName(name);

        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,user.getPassword(), ByteSource.Util.bytes(""),getName()
        );
        return authenticationInfo;
    }
}
