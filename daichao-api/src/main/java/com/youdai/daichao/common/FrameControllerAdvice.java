package com.youdai.daichao.common;

import com.youdai.daichao.common.vo.JsonCodeEnum;
import com.youdai.daichao.common.vo.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 错误代理
 * Created by Administrator on 2017/3/26.
 */
@ControllerAdvice
@Slf4j
public class FrameControllerAdvice {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object processUnauthenticatedException(NativeWebRequest request, Exception e) {
        if (null != e.getMessage() && e.getMessage().contains("The current request is not a multipart request")) {
            e.printStackTrace();
            return "";
        }
        if (null != e.getMessage()) {
            log.error(e.getMessage());
        }
        JsonResp jsonResp;
        if (JsonCodeEnum.OVERTIME.getMessage().equals(e.getMessage())) {
            jsonResp = JsonResp.overtime();
        } else {
            jsonResp = JsonResp.toFail(e.getMessage());
            log.error(e.getMessage(),e); //log4j中 打印出堆栈错误信息
            e.printStackTrace();
        }
        return jsonResp;
    }
}
