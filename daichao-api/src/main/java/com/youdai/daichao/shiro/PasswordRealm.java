package com.youdai.daichao.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.IChannelService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PasswordRealm extends AuthorizingRealm {
    @Autowired
    private IAppUserService userService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 为当前登录的用户授予权限
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //String phone = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }


    /**
     * 验证当前登录的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        //用户名 手机号
        //手机

        //查看UsernamePasswordToken可知，getCredentials()方法的返回值是char []类型的，所以不能直接转化成string。
        char [] ch = (char[]) token.getCredentials();
        //接收输入的密码或验证码
        String password = new String(ch);

        EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
        wrapper.eq("a_uphone",userName);
        //wrapper.eq("status",1);
        AppUser user = userService.selectOne(wrapper);
        if (user != null) {
            //增加逻辑
            //账号 密码 登录
            if(password != null && password.length() == 32) {
                if(!password.equals(user.getPassword())) {
                    throw new IncorrectCredentialsException();
                }
            }else {
                // 短信验证码登录
                //code 过期时间5分钟
                String cacheCode = redisCache.getCache(userName);
                //判断验证码是否正确
                if (cacheCode !=null && !cacheCode.equals(password)) {
                    throw new IncorrectCredentialsException();
                }
            }

            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userName, password, getName());
            return authcInfo;
        }
        EntityWrapper<Channel> wrapper1 = new EntityWrapper<>();
        wrapper1.eq("login_name",userName);
        //wrapper1.eq("status",1);
        Channel channel = channelService.selectOne(wrapper1);
        if (channel != null) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userName, channel.getCPassword(), getName());
            return authcInfo;
        }
        return null;
    }
}	
