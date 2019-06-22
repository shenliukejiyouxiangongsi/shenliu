package com.youdai.daichao.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.constant.PayConstants;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.Orders;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.IOrdersService;
import com.youdai.daichao.util.HttpClientUtil;
import com.youdai.daichao.util.WXParse;
import com.youdai.daichao.util.XMLToMapToBean;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/14
 * @Description  回调通知接受类
 */
@RestController
@RequestMapping(value = "/api/notify")
@Slf4j
public class OrderNotifyController {

    @Autowired
    IAppUserService appUserService;
    @Autowired
    IOrdersService ordersService;
    @Value("${check_url}")
    String check_url;

    /**
     * 微信异步回调
     */
    @RequestMapping(value = "wxNotify",method = RequestMethod.POST)
    public String wxNotify(HttpServletRequest request) throws Exception{
        log.info("微信支付回调");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = null;
        try {
            params = XMLToMapToBean.doXMLParse(resultxml);
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        outSteam.close();
        inStream.close();
        Boolean flag=WXParse.isTenpaySign(params);
        //验签成功
        if(flag){
            String returnCode=params.get("return_code");
            String orderNo=params.get("out_trade_no");
            String tradeNo=params.get("transaction_id");
            EntityWrapper entityWrapper=new EntityWrapper();
            entityWrapper.eq("order_no",orderNo);
            Orders orders=ordersService.selectOne(entityWrapper);
            if("SUCCESS".equals(returnCode)){
                orders.setStatus(1);
                orders.setTradeNo(tradeNo);
                orders.setResultMsg("支付成功");
                orders.setPayTime(params.get("time_end"));
                //获取失败，即返回
//                String result=getReport(orders);
//                if ("ok".equals(result)){
//                    ordersService.updateById(orders);
//                    return returnXML("SUCCESS");
//                }
//                return returnXML("FAIL");
                return returnXML("SUCCESS");
            }
            if("FAIL".equals(returnCode)){
                orders.setStatus(2);
                orders.setTradeNo(tradeNo);
                orders.setResultMsg(params.get("err_code"));
                ordersService.updateById(orders);
                return returnXML("SUCCESS");
            }

        }
        log.info("验签失败");
        return returnXML("FAIL");
    }



    /**
     * 支付宝异步回调
     */
    @RequestMapping(value = "aliNotify",method = RequestMethod.POST)
    public String aliNotify(HttpServletRequest request) throws AlipayApiException {
        log.info("支付宝支付回调");
        Map<String,String> params = XMLToMapToBean.httpParseMap(request);
        boolean flag = AlipaySignature.rsaCheckV1(params, PayConstants.ALIPAY_PUBLIC_KEY, PayConstants.CHARSET,"RSA2");
        //验签成功
        if(flag){
            String tradeNo=params.get("trade_no");
            String orderTime=params.get("gmt_create");
            String orderNo=params.get("out_trade_no");
            EntityWrapper entityWrapper=new EntityWrapper();
            entityWrapper.eq("order_no",orderNo);
            Orders orders=ordersService.selectOne(entityWrapper);
            //订单成功
            if("TRADE_SUCCESS".equals(params.get("trade_status"))){
                orders.setStatus(1);
                orders.setTradeNo(tradeNo);
                orders.setResultMsg("支付成功");
                orders.setOrderTime(orderTime);
                orders.setPayTime(params.get("gmt_payment"));
                //获取失败，即返回
//                String result=getReport(orders);
//                if ("ok".equals(result)){
//                    ordersService.updateById(orders);
//                    return "success";
//                }
//                return result;
                return "success";
            }
            if("TRADE_CLOSED".equals(params.get("trade_status"))){
                orders.setStatus(4);
                orders.setTradeNo(tradeNo);
                orders.setResultMsg("未付款交易超时关闭，或支付完成后全额退款");
                orders.setOrderTime(orderTime);
            }
            if("WAIT_BUYER_PAY".equals(params.get("trade_status"))){
                orders.setTradeNo(tradeNo);
                orders.setResultMsg("交易创建，等待买家付款");
                orders.setOrderTime(orderTime);
            }
            ordersService.updateById(orders);
        }
        log.info("验签失败");
        return "fail";
    }


    public String getReport(Orders orders){
        JSONObject param=new JSONObject();
        param.put("phoneNo",orders.getPhoneNo());
        param.put("idNo",orders.getIdcardNo());
        param.put("idName",orders.getIdcardName());
        param.put("channelUserId",orders.getUserId());
        EntityWrapper wrapper=new EntityWrapper();
        wrapper.eq("a_uid",orders.getUserId());
        AppUser user=appUserService.selectOne(wrapper);
        param.put("channelPhoneNo",user.getAUphone());
        param.put("channel","daichao");
        param.put("appName",orders.getPhoneType());
        try {
            String result = HttpClientUtil.postParameters(check_url, JSONObject.toJSONString(param));
            JSONObject jsonResult = JSON.parseObject(result);
            if(null!=jsonResult){
                Boolean success=jsonResult.getBoolean("success");
                if (success){
                    String reportNumber=jsonResult.getString("msg");
                    orders.setReportStatus(1);
                    orders.setReportNumber(reportNumber);
                    return "ok";
                }
            }
            System.out.println(result);
        } catch (IOException e) {
            orders.setReportStatus(2);
            e.printStackTrace();
        }
        return "fail";
    }

    private String returnXML(String return_code) {
        return "<xml><return_code><![CDATA["
                + return_code
                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

}
