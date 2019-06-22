package com.youdai.daichao.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.youdai.daichao.common.constant.PayConstants;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/13
 * @Description 支付宝统一支付
 */
@Service
@Slf4j
public class AliUnionPay {

    @Value("${ali_notify_url}")
    String aliNotifyUrl;

    //实例化客户端
    AlipayClient alipayClient = new DefaultAlipayClient(PayConstants.ALI_URL, PayConstants.ALI_APP_ID,PayConstants.ALI_APP_PRIVATE_KEY, "json", PayConstants.CHARSET, PayConstants.ALIPAY_PUBLIC_KEY,PayConstants.sign_type);
    //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

    //调用支付宝客户端
    public JsonResp alipay(Orders order) {

        Map<String,Object> resultMap=new HashMap<>();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("溜溜球");//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
        model.setSubject(order.getMercName());//商品的标题/交易标题/订单标题/订单关键字等
        model.setTotalAmount(order.getMount());//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        model.setProductCode("QUICK_MSECURITY_PAY");//销售产品码，商家和支付宝签约的产品码
        model.setOutTradeNo(order.getOrderNo());//商户网站唯一订单号，请保证OutTradeNo值每次保证唯一
        model.setTimeoutExpress("30m");//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        request.setBizModel(model);
        request.setNotifyUrl(aliNotifyUrl);//商户外网可以访问的异步地址
        AlipayTradeAppPayResponse response;
        try {
            response = alipayClient.sdkExecute(request);//这里和普通的接口调用不同，使用的是sdkExecute
            resultMap.put("zfb",response.getBody());
            resultMap.put("type",1);
            return JsonResp.ok(resultMap);//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
            String massage = "alipay.trade.app.pay接口：订单签名错误";
            log.info("订单号为:"+order.getTradeNo());
            return JsonResp.fa("订单号:"+order.getTradeNo()+massage);
        }
    }


}
