package com.youdai.daichao.shiro;

import org.apache.shiro.SecurityUtils;

public class SecurityUtil {

    public static Object getUser() {
        Object userName =  SecurityUtils.getSubject().getPrincipal();

        return null;
    }
}
