package com.youdai.daichao.common.vo;

import lombok.Data;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/14
 * @Description     支付请求的返回类
 */
@Data
public class OrderRes {

    private String outTradeNo;

    private String state;

    private String returnCode;

    /**通道*/
    private String type;

    /**微信预支付交易会话标识*/
    private String prepay_id;
}
