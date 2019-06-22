package com.youdai.daichao.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.UserRecord;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.IUserRecordService;
import com.youdai.daichao.util.RequestUtil;
import com.youdai.daichao.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("进入拦截");
        //设备唯一标识
        String  deviceFlag=request.getHeader("deviceFlag");
        String  phone=request.getHeader("userPhone");
        String  type=request.getHeader("type");
        String ip = RequestUtil.getIpAddr(request);
        //有标识即为移动端设备
        if(null!=type&&!"".equals(type)){
            insertApp(type,deviceFlag,ip,phone);
        }else {//web端请求
            String userAgent= request.getHeader("user-agent");
            insertWeb(userAgent,ip);
        }
        return true;
    }



    //方法加锁，防止同一时刻插入信息
    public synchronized void insertApp(String type,String deviceFlag,String ip,String phone){
        EntityWrapper wrapper=new EntityWrapper();
        EntityWrapper entityWrapper=new EntityWrapper();
        wrapper.eq("type",Integer.valueOf(type));
        wrapper.eq("udid",deviceFlag);
        wrapper.eq("ip",ip);
        UserRecord record=userRecordService.selectOne(wrapper);
        if(record==null){
            UserRecord userRecord=new UserRecord();
            userRecord.setUdid(deviceFlag);
            userRecord.setIp(ip);
            userRecord.setType(Integer.parseInt(type));
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
    public synchronized void insertWeb(String userAgent,String ip){
        EntityWrapper wrapper=new EntityWrapper();
        wrapper.eq("user_agent",userAgent);
        wrapper.eq("ip",ip);
        UserRecord record=userRecordService.selectOne(wrapper);
        if(record==null){
            UserRecord userRecord=new UserRecord();
            userRecord.setIp(ip);
            userRecord.setUserAgent(userAgent);
            userRecordService.insert(userRecord);
            redisCache.putCache(ip+userAgent,userRecord);
        }
    }
}
