package com.youdai.daichao.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.youdai.daichao.util.RequestEncoder;
import com.youdai.daichao.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@Slf4j
public class MessageXsendService {
 //   private Logger log = Logger.getLogger(this.getClass());


    /**
     * 时间戳接口配置
     */
    public static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";
    public static final String appid = "32204";
    public static final String appkey = "63e77381625b988c89952181c59e94b5";
    public static final String signtype = "md5";
    public static final String url = "https://api.mysubmail.com/message/xsend";

//    @Value("${chuanglan.sms_url}")
    private static String cl_sms_url = "http://smssh1.253.com/msg/send/json";
//    @Value("${chuanglan.account}")
    private static String cl_account = "N7419788";
//    @Value("${chuanglan.password}")
    private static String cl_password = "4cXwa27vV";

    private static Map<String,String> param = new HashMap<>();
    static {
        param.put("account",cl_account);
        param.put("password",cl_password);
        param.put("report","true");

    }



    /**
     * 备用接口
     * https://api.submail.cn/service/timestamp
     */

    public static final String TYPE_MD5 = "md5";
    public static final String TYPE_SHA1 = "sha1";

    public Map<String, String> smsXsend(String phone, Map<String, String> map, String project) {


        TreeMap<String, Object> requestData = new TreeMap<String, Object>();
        JSONObject vars = new JSONObject();
        for (String key : map.keySet()) {
            String value = map.get(key);
            vars.put(key, value);
        }
        /**
         *  签名验证方式
         */

        requestData.put("appid", appid);
        requestData.put("project", project);
        requestData.put("to", phone);
        if (!vars.isEmpty()) {
            requestData.put("vars", vars.toString());
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        @SuppressWarnings("deprecation")
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
        for (Map.Entry<String, Object> entry : requestData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                builder.addTextBody(key, String.valueOf(value), contentType);
            }
        }
        if (signtype.equals(TYPE_MD5) || signtype.equals(TYPE_SHA1)) {
            String timestamp = getTimestamp();
            requestData.put("timestamp", timestamp);
            requestData.put("sign_type", signtype);
            String signStr = appid + appkey + RequestEncoder.formatRequest(requestData) + appid + appkey;

            builder.addTextBody("timestamp", timestamp);
            builder.addTextBody("sign_type", signtype);
            builder.addTextBody("signature", RequestEncoder.encode(signtype, signStr), contentType);
        } else {
            builder.addTextBody("signature", appkey, contentType);
        }
        /**
         * http post 请求接口
         * 成功返回 status: success,其中 fee 参数为短信费用 ，credits 参数为剩余短信余额
         */
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("charset", "UTF-8");
        httpPost.setEntity(builder.build());
        Map<String, String> rest = new HashMap<>();
        Gson gson = new Gson();
        try {
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                rest = gson.fromJson(EntityUtils.toString(httpEntity, "UTF-8"), rest.getClass());
               // log.debug("返回结果" + rest);
            }
        } catch (ClientProtocolException e) {
            //log.error("手机号：" + phone + "：短信发送异常");
            e.printStackTrace();
        } catch (IOException e) {
           // log.error("手机号：" + phone + "：短信发送异常");
            e.printStackTrace();
        }
        return rest;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    private String getTimestamp() {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        String timestamp = TIMESTAMP;
        HttpGet httpget = new HttpGet(timestamp);
        try {
            HttpResponse response = closeableHttpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
            if (jsonStr != null) {
                JSONObject json = JSONObject.parseObject(jsonStr);
                return json.getString("timestamp");
            }
            closeableHttpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *创蓝短信发送
     *             "account" : "N6000001", //用户在253云通讯平台上申请的API账号
     *             "password" : "123456", //用户在253云通讯平台上申请的API账号对应的API密钥
     *             "msg" : "【253】您的验证码是：2530", //短信内容。长度不能超过536个字符
     *             "phone" : "15800000000", //手机号码。多个手机号码使用英文逗号分隔
     *             "sendtime" : "201704101400", //定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，不填则默认为立即发送（选填参数）
     *             "report" : "true", //是否需要状态报告（默认为false）（选填参数）
     *             "extend" : "555", //用户自定义扩展码，纯数字，建议1-3位（选填参数）
     *             "uid" : "批次编号-场景名（英文或者拼音）" //自助通系统内使用UID判断短信使用的场景类型，可重复使用，可自定义场景名称，示例如 VerificationCode（选填参数）
     */
    public Map<String, String> smsXsendCL(String phone, Map<String, String> map,String sendTime, String extend,String uid) {
        Map<String,String> result = new HashMap<>();
        map.putAll(param);
        map.put("phone",phone);
        if(!StringUtils.isEmpty(sendTime)) map.put("sendtime",sendTime);
        if(!StringUtils.isEmpty(extend)) map.put("extend",extend);
        if(!StringUtils.isEmpty(uid)) map.put("uid",uid);
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("手机号："+phone+", 短信发送内容: "+JSONObject.toJSONString(map));
            result =  restTemplate.postForObject(cl_sms_url,map,Map.class);
            log.info("手机号："+phone+", 短信发送结果: "+JSONObject.toJSONString(result));
            if("0".equals(result.get("code"))) result.put("status","success");
        }catch (Exception e) {
            log.error("短信发送异常，手机号：" + phone,e);
        }
        return result;
    }

    public Map<String, String> smsXsendCL(String phone, Map<String, String> map) {
        return smsXsendCL(phone, map,null,null,null);
    }


    public static void main(String[] args) {
        Map<String,String> result = new HashMap<>();
        Map map = new HashMap();
        map.putAll(param);
        map.put("phone","19941102119");
        map.put("msg","【企鹅花花】hello world");
        String phone = "19941102119";

        RestTemplate restTemplate = new RestTemplate();
        try {
            result =  restTemplate.postForObject(cl_sms_url,map,Map.class);
            log.debug("手机号："+phone+", 短信发送结果: "+JSONObject.toJSONString(result));
            if("0".equals(result.get("code"))) result.put("status","success");
        }catch (Exception e) {
            log.error("短信发送异常，手机号：" + phone,e);
        }
    }
}
