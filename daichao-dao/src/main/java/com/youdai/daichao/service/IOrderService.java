package com.youdai.daichao.service;

import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Orders;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/13
 * @Description
 */
public interface IOrderService {

    JsonResp pay(Orders order);
}
