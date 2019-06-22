package com.youdai.daichao.service.impl;

import com.youdai.daichao.common.constant.PayConstants;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Orders;
import com.youdai.daichao.util.DateUtils;
import com.youdai.daichao.util.WXParse;
import com.youdai.daichao.util.XMLToMapToBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/13
 * @Description 微信统一支付
 */
@Service
@Slf4j
public class WXUnionPay {

    @Value("${wx_notify_url}")
    String wxNotifyUrl;

    /**
     * 统一下单接口
     *
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    public JsonResp pay(Orders order) throws Exception {
        Map<String,Object> resultMap=new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("appid","wx2421b1c4370ec43b");
        map.put("attach",order.getMercName());
        map.put("body",order.getMercName());
        //商户号
        map.put("mch_id","16845416");
        map.put("nonce_str", DateUtils.dateTimeNow(DateUtils.YYYYMMDDHHMMSS));
        map.put("out_trade_no",order.getOrderNo());
        map.put("total_fee",order.getMount());
        map.put("trade_type","APP");
        map.put("spbill_create_ip","14.23.150.211");
        map.put("notify_url",wxNotifyUrl);
        //验签
        String  sign= WXParse.getReqSign(map, PayConstants.WEIXIN_APIKEY);
        map.put("sign",sign);
        //map转xml
        String sendXml = XMLToMapToBean.mapToXMLTest1(map);
        log.info("微信订单支付发送地址={},发送消息={}", PayConstants.unityPayUrl, sendXml);
        //得到微信返回信息,xml文件
        String res = XMLToMapToBean.sendPost(PayConstants.unityPayUrl, sendXml);
        //xml转map
        Map<String, Object> resMap = XMLToMapToBean.xmlStr2Map(res);
        resultMap.put("type",2);
        resultMap.put("wx",resMap);
        return  JsonResp.ok(resultMap);
    }


}
