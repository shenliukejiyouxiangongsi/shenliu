package com.youdai.daichao.service.impl;

import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Orders;
import com.youdai.daichao.mapper.OrdersMapper;
import com.youdai.daichao.service.IOrderService;
import com.youdai.daichao.util.DateUtils;
import com.youdai.daichao.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/13 0013
 * @Description
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    AliUnionPay aliUnionPay;
    @Autowired
    WXUnionPay wxUnionPay;
    @Autowired
    OrdersMapper ordersMapper;


    @Override
    public JsonResp pay(Orders order) {
        String idCardNo=order.getIdcardNo();
        String phone=order.getPhoneNo();
        if(!ValidateUtils.verify(idCardNo)){
            return JsonResp.fa("身份证格式有误");
        }
        if(!ValidateUtils.isMobile(phone)){
            return JsonResp.fa("请核对手机号");
        }
        String outTradeNo= DateUtils.dateTimeNow(DateUtils.YYYYMMDDHHMMSS);
        order.setOrderNo(outTradeNo);
        //保存订单信息
        order.setStatus(3);
        ordersMapper.insert(order);
        //根据类型不同，选择对应的支付通道
        int type=order.getChannel();
        if (1==type){
            return aliUnionPay.alipay(order);
        }
        if (2==type){
            try {
                return wxUnionPay.pay(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
