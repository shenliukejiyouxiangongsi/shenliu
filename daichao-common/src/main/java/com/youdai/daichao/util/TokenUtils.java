package com.youdai.daichao.util;

import java.util.UUID;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/12
 * @Description
 */
public class TokenUtils {

    /**
     * 生成token
     * @return
     */
    public static String getToken()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
