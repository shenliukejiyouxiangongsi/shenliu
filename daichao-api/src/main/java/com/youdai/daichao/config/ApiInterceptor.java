package com.youdai.daichao.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.common.enums.ChannelStatusEnum;
import com.youdai.daichao.common.enums.ClientType;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.domain.UserRecord;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.IChannelService;
import com.youdai.daichao.service.IUserRecordService;
import com.youdai.daichao.util.Md5;
import com.youdai.daichao.util.RequestUtil;
import com.youdai.daichao.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/14
 * @Description   拦截器
 */
@Component
@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCache redisCache;
    @Autowired
    IUserRecordService userRecordService;
    @Autowired
    IAppUserService appUserService;
    @Autowired
    IChannelService channelService;
    @Value("${spring.profiles.active}")
    private String active;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("进入拦截");
        //设备唯一标识
        String  deviceFlag=request.getHeader("deviceFlag");
        String  phone=request.getHeader("userPhone");
        String userAgent= request.getHeader("user-agent");
        String type = RequestUtil.getClientType(request);
        String ip = RequestUtil.getIpAddr(request);
        log.info("phone-------------"+phone);
        log.info("ip-------------"+ip);

        //用户来源表
        insertUserRecord(request);

//        if("dev".equals(active)) return true;

        //h5放行
        if(request.getRequestURI().indexOf("/h5") > -1) {
            return true;
        }

        //MD5
        if(!getAllParams(request)) {
            log.error("sign 检验失败！");
            try {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                JsonResp resp = JsonResp.fa("sign 检验失败！");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(resp));
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("sign 检验异常",e);
            }

            return false;
        };
        return true;
    }



    //方法加锁，防止同一时刻插入信息
    public synchronized void insertApp(String type,String deviceFlag,String ip,String phone,String userAgent){
        EntityWrapper wrapper=new EntityWrapper();
        EntityWrapper entityWrapper=new EntityWrapper();
        wrapper.eq("type",Integer.valueOf(type));
        if(null != deviceFlag) {
            wrapper.eq("udid",deviceFlag);
        }
        wrapper.eq("ip",ip);
        UserRecord record=userRecordService.selectOne(wrapper);
        if(record==null){
            UserRecord userRecord=new UserRecord();
            userRecord.setUdid(deviceFlag);
            userRecord.setIp(ip);
            userRecord.setType(Integer.parseInt(type));
            userRecord.setUserAgent(userAgent);
            if(StringUtil.isNotEmpty(phone)){
                userRecord.setUserPhone(phone);
                entityWrapper.eq("a_uphone",phone);
                AppUser user=appUserService.selectOne(entityWrapper);
                userRecord.setChannelId(user.getChannelId());
            }
            userRecordService.insert(userRecord);
            redisCache.putCache(deviceFlag+ip,userRecord);
        }else {
            if(StringUtil.isNotEmpty(phone)&&redisCache.haveCache(deviceFlag+ip) == true){
                UserRecord userRecord=redisCache.getCache(deviceFlag+ip);
                if(!phone.equals(userRecord.getUserPhone())){
                    entityWrapper.eq("a_uphone",phone);
                    AppUser user=appUserService.selectOne(entityWrapper);
                    userRecord.setUserPhone(phone);
                    userRecord.setChannelId(user.getChannelId());
                    userRecordService.updateById(userRecord);
                    redisCache.putCache(deviceFlag,userRecord);
                }
            }

        }
    }

    //方法加锁，防止同一时刻插入信息
    public synchronized void insertWeb(String userAgent,String  deviceFlag,String ip){
        EntityWrapper wrapper=new EntityWrapper();
        wrapper.eq("user_agent",userAgent);
        wrapper.eq("ip",ip);
        UserRecord record=userRecordService.selectOne(wrapper);
        if(record==null){
            UserRecord userRecord=new UserRecord();
            userRecord.setIp(ip);
            userRecord.setUserAgent(userAgent);
            userRecord.setUdid(deviceFlag);
            userRecordService.insert(userRecord);
            redisCache.putCache(ip+userAgent,userRecord);
        }
    }

    public boolean getAllParams(HttpServletRequest request) throws Exception{
        Enumeration<String> parameterNames = request.getParameterNames();
        //没有传参数放行
        if(!parameterNames.hasMoreElements()) return true;

        StringBuilder sb = new StringBuilder();
        String signValue = null;
        Map<String,String> treemap = new TreeMap();
        while(parameterNames.hasMoreElements()) {
            String paramName =  parameterNames.nextElement();
            if("sign".equals(paramName)){
                signValue = request.getParameter(paramName);
            }
            else {
                String paramValue = request.getParameter(paramName);
                treemap.put(paramName,paramValue);
            }
        }
        for (Map.Entry<String,String> e:treemap.entrySet()){
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        sb.append("serverAPI=qehh");
        log.info("请求的url--------"+request.getRequestURI());
        log.info("请求的user-agent--------"+request.getHeader("user-agent"));
        log.info("请求的参数---------"+sb.toString());
        log.info("sign----------"+ signValue);
        log.info("md5-----------"+Md5.md5Encode(sb.toString()));
        if(Md5.md5Encode(sb.toString()).equals(signValue)) return true;
        return false;
    }

    public synchronized void insertUserRecord(HttpServletRequest request) {
        //手机唯一标示，可能没有
        String  deviceFlag=request.getHeader("deviceFlag");
        String  phone=request.getHeader("userPhone");
        String userAgent= request.getHeader("user-agent");
        //手机类型
        String type = RequestUtil.getClientType(request);
        if(null == type) {
            type = request.getHeader("type");
            if(null == type) type = ClientType.ANDROID.getCode();
        }
        String ip = RequestUtil.getIpAddr(request);
        String channelName = request.getParameter("channelName");
        Wrapper<UserRecord> userRecordwrapper = new EntityWrapper<>();
//        userRecordwrapper.eq("user_agent",userAgent);
//        userRecordwrapper.eq("type",type);
        userRecordwrapper.eq("ip",ip);
        int channelId = 0;
        //一般只有渠道推广进h5才有channelName
        if(!StringUtil.isEmpty(channelName)) {
            Wrapper<Channel> channelwrapper = new EntityWrapper<>();
            channelwrapper.eq("c_loginname", channelName);
            channelwrapper.eq("status", ChannelStatusEnum.ONLINE.getStatus());
            Channel channel = channelService.selectOne(channelwrapper);
            if (channel != null) {
                channelId = channel.getChannelId();
                userRecordwrapper.eq("channel_id",channelId);
            }
        }
        UserRecord userRecord = userRecordService.selectOne(userRecordwrapper);
        if(null == userRecord) {
            userRecord = new UserRecord();
            userRecord.setUserAgent(userAgent);
            userRecord.setIp(ip);
            userRecord.setType(Integer.parseInt(type));
            if(0 != channelId) {
                userRecord.setChannelId(channelId);
            }
            if(StringUtil.isNotEmpty(phone)){
                userRecord.setUserPhone(phone);
            }
            userRecord.setUdid(deviceFlag);
            userRecordService.insert(userRecord);
            // 和用户注册表关联
            request.setAttribute("userRecordId",userRecord.getId());
            redisCache.putCache(ip,userRecord);
            return;
        }
        if(!redisCache.haveCache(ip)){
            redisCache.putCache(ip,userRecord);
        }
        request.setAttribute("userRecordId",userRecord.getId());

    }

    public void returnJson(JsonResp resp,HttpServletResponse response) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(JSON.toJSONString(resp));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("sign 检验异常",e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
