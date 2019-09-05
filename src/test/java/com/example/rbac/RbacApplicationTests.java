package com.example.rbac;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RbacApplicationTests {

    @Test
    public void contextLoads() {
        //盐值用的用的是对用户名的加密（测试用的"lisi"）
        ByteSource credentialsSalt01 = ByteSource.Util.bytes("");
        Object salt = null;//盐值
        Object credential = "tangzhihong";//密码
        String hashAlgorithmName = "MD5";//加密方式
        //1024指的是加密的次数
        Object simpleHash = new SimpleHash(hashAlgorithmName, credential,
                credentialsSalt01, 2);
        System.out.println("加密后的值----->" + simpleHash);
    }

}
