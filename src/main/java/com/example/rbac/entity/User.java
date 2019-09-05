package com.example.rbac.entity;

import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String userName;
    private String userId;
    private String password;
//    private String salt;
    private List<SysRole> roleList;

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }


    public  User(){

    }

    public User(int id, String userName, String userId, String password) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
