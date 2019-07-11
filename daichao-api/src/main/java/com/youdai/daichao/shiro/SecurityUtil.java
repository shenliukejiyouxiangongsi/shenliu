package com.youdai.daichao.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.service.IAppUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityUtil {

    private static IAppUserService userService;

    public static Object getUser() {
        Object userName =  SecurityUtils.getSubject().getPrincipal();
        if(null == userName) return null;
        Wrapper<AppUser> wrapper = new EntityWrapper<>();
        wrapper.eq("a_uphone",userName);
        //wrapper.eq("status",1);
        AppUser user = userService.selectOne(wrapper);
        return user;
    }

    @Autowired
    public void setUserService(IAppUserService userService) {
        SecurityUtil.userService = userService;
    }
}
