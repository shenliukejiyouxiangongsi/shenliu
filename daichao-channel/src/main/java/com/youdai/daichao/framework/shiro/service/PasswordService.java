package com.youdai.daichao.framework.shiro.service;


import com.youdai.daichao.common.constant.Constants;
import com.youdai.daichao.common.exception.user.UserPasswordNotMatchException;
import com.youdai.daichao.common.utils.MessageUtils;
import com.youdai.daichao.framework.manager.AsyncManager;
import com.youdai.daichao.framework.manager.factory.AsyncFactory;
import com.youdai.daichao.project.system.user.domain.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 * 
 * @author user-xmp
 */
@Component
public class PasswordService
{
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;


    private final String SYS_LOGIN_RECORD_CACHE_PRIXE = "sys_login_record_cache_";

    public void validate(SysUser user, String password)
    {
        String loginName = user.getUserName();


        String key = SYS_LOGIN_RECORD_CACHE_PRIXE+ loginName;
        AtomicInteger retryCount = loginRecordCache.get(key);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(key, retryCount);
        }

        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(key, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(key);
        }
    }





    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public boolean matches(SysUser user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getUserName(), newPassword));
    }

    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex();
    }

    public String encryptPassword(String username, String password)
    {
        return new Md5Hash(username + password ).toHex();
    }

    public static void main(String[] args){
        System.out.print(new Md5Hash("admin123456abcd").toHex());
    }
}
