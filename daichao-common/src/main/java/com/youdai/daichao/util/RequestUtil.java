package com.youdai.daichao.util;

import com.youdai.daichao.common.enums.ClientType;

import javax.servlet.http.HttpServletRequest;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/22
 * @Description
 */
public class RequestUtil {

    public static String getIpAddr(HttpServletRequest request)
    {
        if (request == null)
        {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static String getClientType(HttpServletRequest request) {
        String type = ClientType.WEB.getCode();
        String userAgent= request.getHeader("user-agent");
        if(null != userAgent) {
            if(userAgent.indexOf("Android") > -1 || userAgent.indexOf("okhttp") > -1) {
                type = ClientType.ANDROID.getCode();
            }else if(userAgent.indexOf("iPhone") > -1) {
                type = ClientType.IOS.getCode();
            }else {
                type = ClientType.WEB.getCode();
            }
        }
        return type;
    }
}
