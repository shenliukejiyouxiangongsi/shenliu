package com.youdai.daichao.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youdai.daichao.util.AesEncryptUtil;
import com.youdai.daichao.util.EncryptedString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiSignFilter implements Filter {

    private static String[] excludedUris = {
            "/h5","/getPass","/getVersion",
            "/getPhoneCodeV2","/getPhoneCode","/phoneCodeLogin","/login"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        String url = ((HttpServletRequest)request).getRequestURI();
        String version = ((HttpServletRequest)request).getHeader("version");
        if(isExcludedUri(url) || null == version){
            filterChain.doFilter(request, response);
            return;
        }
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);//转换成代理类
        filterChain.doFilter(request,wrapperResponse);
        byte[] content = wrapperResponse.getContent();//获取返回值
        //判断是否有值
        if (content.length > 0) {
            String res = new String(content,"UTF-8");
            try {
                log.info("加密前------"+res);
                res = AesEncryptUtil.encrypt(res);
                log.info("加密后------"+res);
                Map map = new HashMap();
                map.put("key",res);
                res = JSON.toJSONString(map);
            }catch (Exception e) {
                log.error("加密异常，异常信息-----"+e);
            }
            //把返回值输出到客户端
            ServletOutputStream out = response.getOutputStream();
            out.write(res.getBytes());
            out.flush();
        }

    }

    @Override
    public void destroy() {

    }

    private boolean isExcludedUri(String uri) {
        if (excludedUris == null || excludedUris.length <= 0) {
            return false;
        }
        for (String ex : excludedUris) {
            uri = uri.trim();
            ex = ex.trim();
            if (uri.indexOf(ex)> -1)
                return true;
        }
        return false;
    }
}
