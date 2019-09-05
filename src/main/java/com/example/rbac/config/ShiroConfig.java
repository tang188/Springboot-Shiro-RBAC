package com.example.rbac.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 详细filter Name 查看: http://shiro.apache.org/web.html#Web-FilterChainDefinitions
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        logger.info("---------->>> ShiroFilterFactoryBean.shiroFilter");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        /**
         * anon : 不拦截
         * authc:拦截全部
         */
        filterChainMap.put("/css/**","anon");
        filterChainMap.put("/fonts/**","anon");
        filterChainMap.put("/img/**","anon");
        filterChainMap.put("/static/**","anon");
        filterChainMap.put("/js/**","anon");
        filterChainMap.put("/html/**","anon");

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/403");

//        filterChainMap.put("/layout","layout");
        filterChainMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }
    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

    @Bean
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        //指定解密算法
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        return manager;
    }
    /**
     * shiro拦截基于AOP
     * 开启aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 抛出的异常进行统一处理跳转
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("UnauthorizedException", "/user/403");
        resolver.setExceptionMappings(mappings);  // None by default
        resolver.setDefaultErrorView("error");    // No default
        resolver.setExceptionAttribute("exception");     // Default is "exception"
        return resolver;
    }

    public static void main(String[] args) {
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
