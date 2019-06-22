package com.youdai.daichao.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.domain.ChannelCountLog;
import com.youdai.daichao.domain.UserCountLog;
import com.youdai.daichao.service.*;
import com.youdai.daichao.sms.SmsAPI;
import com.youdai.daichao.util.Base64Picture;
import com.youdai.daichao.util.ImageUtil;
import com.youdai.daichao.util.Md5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/20
 * @Description
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class WebController {

    @Autowired
    IAppUserService userService;
    @Autowired
    IChannelService channelService;
    @Autowired
    IUserCountLogService userCountLogService;
    @Autowired
    IChannelCountLogService channelCountLogService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    MessageService messageService;
    @Value("${chuanglan.templateId.registerMsgId}")
    String registerMsgId;

    /** web端用户注册
     * @param phone 手机号
     * @param password  密码
     * @param code  验证码
     * @param channelId 渠道id
     * @return
     */
    @RequestMapping(value = "registerWeb" , method = RequestMethod.GET)
    public String  registerWeb(String phone,String password,String code,String channelId,@RequestParam(value = "proKey",required=false) String proKey,String picCode, HttpSession session){
        log.info("web端用户注册");
        JSONObject json=new JSONObject();
        //图片验证码
        if(null!=picCode&&!"".equals(picCode)){
            String picCode1 = redisCache.getCache(session.getId() + "code");
            if(!picCode.equals(picCode1)){
                json.put("code","2");
                json.put("msg","图形验证码错误");
                return "callback("+json.toString()+")";
            }
        }
        String code1 = redisCache.getCache(phone);
        if (!code.equals(code1)) {
            json.put("code","2");
            json.put("msg","短信验证码错误");
            return "callback("+json.toString()+")";
        }
        //如果需要验证码，可以先做验证
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("a_uphone", phone);
        AppUser appUser=userService.selectOne(entityWrapper);
        if(null!=appUser){
            json.put("code","2");
            json.put("msg","该手机号已经注册过，请直接登录！");
            return "callback("+json.toString()+")";
        }
        AppUser user=new AppUser();
        user.setProKey(proKey);
        //判断是否从渠道商进来
        if(null!=channelId && !"".equals(channelId)&&!"null".equals(channelId) && !"undefined".equals(channelId)){
            EntityWrapper wrapper=new EntityWrapper();
            wrapper.eq("channel_id",channelId);
            wrapper.eq("status",1);
            Channel channel=channelService.selectOne(wrapper);
            if(null==channel){
                json.put("code","2");
                json.put("msg","未找到相应的渠道商！");
                return "callback("+json.toString()+")";
            }
            ChannelCountLog channelCountLog=new ChannelCountLog();
            channelCountLog.setRegisterNum(1);
            channelCountLog.setChannelId(Integer.valueOf(channelId));
            channelCountLogService.insert(channelCountLog);
            user.setChannelId(Integer.valueOf(channelId));
        }
        if (StringUtils.length(password) < 6) {
            json.put("code","2");
            json.put("msg","密码长度请限制在6位以上！");
            return "callback("+json.toString()+")";
        }
        user.setAUphone(phone);
        user.setEquipmentFlag(2);
        user.setStatus(1);
        user.setPassword(Md5.md5Encode(password));
        userService.insert(user);
        //插入日志表
        UserCountLog userCountLog=new UserCountLog();
        userCountLog.setRegisterNum(1);
        userCountLog.setPhone(phone);
        userCountLogService.insert(userCountLog);
        json.put("code","1");
        json.put("msg","成功");
        return "callback("+json.toString()+")";
    }

    /**
     * web端获取图片验证码
     * @param response
     * @param session
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create/pictureCodeWeb", method = RequestMethod.GET)
    public String valicodeWeb(HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {

        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = ImageUtil.createImage();
        //将验证码code存入redis
        redisCache.putCacheWithExpireTime(session.getId() + "code", objs[0], 2 * 60);
        log.debug(session.getId());
        //将图片转化base传递给前端页面
        BufferedImage image = (BufferedImage) objs[1];
        String base64 = Base64Picture.putImage(image);
        JSONObject json=new JSONObject();
        json.put("success",true);
        json.put("base64", base64);
        return "picCallback("+json.toString()+")";
    }

    /**
     * @return 返回值JsonResp
     * @获取验证码(web端)
     */
    @RequestMapping(value = "/getPhoneCodeWeb", method = RequestMethod.GET)
    public String getPhoneCodeweb(String phone, String picCode, HttpSession session) {
        log.debug("获取验证码");
        JSONObject json=new JSONObject();
        String code = SmsAPI.randomString(4);
        Map<String, String> para = new HashMap<>();
        para.put("code", code);
        messageService.send(phone, para, registerMsgId);
        redisCache.putCacheWithExpireTime(phone, code, 120);
        json.put("code","1");
        json.put("success",true);
        json.put("msg","短信已发送");
        return "sendMsgCallback("+json.toString()+")";
    }



}
