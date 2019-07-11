package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.common.vo.CheckBody;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.*;
import com.youdai.daichao.service.*;
import com.youdai.daichao.sms.SmsAPI;
import com.youdai.daichao.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.TestOnly;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @Anthor: zhankui
 * @Date: 2019/3/12
 * @Description  app用户控制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    IAppUserService userService;
    @Autowired
    IChannelService channelService;
    @Autowired
    IUserRecordService userRecordService;
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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**登陆
     * @param phone  手机号
     * @param password  密码
     * @return
     */
    @RequestMapping(value = "login")
    public JsonResp  login(String phone,String password,HttpServletRequest request ){


        log.info("app用户登陆,手机号："+ phone);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("a_uphone", phone);
        AppUser appUser=userService.selectOne(entityWrapper);
        if(null==appUser){
            return JsonResp.fa("您未注册，请先注册");
        }
        entityWrapper.eq("password",Md5.md5Encode(password));
        AppUser user=userService.selectOne(entityWrapper);
        if(null==user){
            return JsonResp.fa("您输入的密码错误");
        }
        insertLog(user,phone,request,false);
        return JsonResp.ok(userService.loginByPhone(phone, password));
    }




    /**
     * @param
     * @return 返回值JsonResp
     * @根据id查找用户
     */
    @RequestMapping(value = "/phoneCodeLogin", method = RequestMethod.GET)
    public JsonResp phoneCodeLogin(String phone, String code, HttpServletRequest request) {
        log.info("手机验证码登录,手机号："+ phone);
        String code1 = redisCache.getCache(phone);
        if (!code.equals(code1)) {
            return JsonResp.fa("验证码错误");
        }
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("a_uphone", phone);
        AppUser user = userService.selectOne(entityWrapper);
        if (user == null) {
            return JsonResp.fa("非法登录，请通过渠道登录！");
//            user = new AppUser();
//            user.setAUphone(phone);
//            userService.insert(user);
//            user = userService.phoneCodeLogin(phone, code);
//            insertLog(user,phone,request,true);
//            return JsonResp.ok(user);
        } else {
            user = userService.phoneCodeLogin(phone, code);
            insertLog(user,phone,request,false);
            //校验成功 删除key
//            redisCache.delCache(phone);
            return JsonResp.ok(user);
        }

    }

    public void insertLog(AppUser user,String phone,HttpServletRequest request, boolean isRegister){
        //插入日志表
        UserCountLog userCountLog=new UserCountLog();
        if(isRegister) userCountLog.setRegisterNum(1);
        userCountLog.setLoginNum(1);
//        Object userRecordId = request.getAttribute("userRecordId");
//        if(userRecordId != null) {
//            userCountLog.setDeviceFlag(userRecordId.toString());
//            user.setUserRecordId(Long.parseLong(userRecordId.toString()));
//        }
        userCountLog.setDeviceFlag(user.getUserRecordId().toString());
        userCountLog.setPhone(phone);
        userCountLogService.insert(userCountLog);
        if(0!=user.getChannelId()){
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("record_id",user.getUserRecordId());
            wrapper.eq("user_id",user.getAUid());
            wrapper.eq("login_num",1);
            wrapper.gt("create_time", DateUtils.getDayStartDate(new Date()));
            ChannelCountLog channelCountLog = channelCountLogService.selectOne(wrapper);
            //去重
            if(null == channelCountLog) {
                channelCountLog = new ChannelCountLog();
                if (isRegister) channelCountLog.setRegisterNum(1);
                channelCountLog.setLoginNum(1);
                channelCountLog.setChannelId(user.getChannelId());
                channelCountLog.setUserId(user.getAUid());
                channelCountLog.setClientType(RequestUtil.getClientType(request));
                channelCountLog.setRecordId(user.getUserRecordId());
                channelCountLogService.insert(channelCountLog);
            }
        }
    }

    /** 用户注册
     * @param phone 手机号
     * @param password  密码
     * @param code  验证码
     * @param channelName 渠道名称
     * @return
     */
    @RequestMapping(value = "register" , method = RequestMethod.GET)
    public JsonResp  register(String phone,String password,String code,String channelName,int equipmentFlag,HttpServletRequest request ){
        log.info("app用户注册,手机号："+ phone);

        String code1 = redisCache.getCache(phone);
        if (!code.equals(code1)) {
            return JsonResp.fa("验证码错误");
        }
         String proKey=request.getHeader("proKey");
        if(proKey == null||!"yddcApp".equals(proKey)){
            return JsonResp.fa("header未带proKey或者proKey不正确");
        }
        //如果需要验证码，可以先做验证
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("a_uphone", phone);
        AppUser appUser=userService.selectOne(entityWrapper);
        if(null!=appUser){
            return JsonResp.fa("该手机号已经注册过，请直接登录");
        }

        //不是从渠道进来，防止撸贷
        if(null ==channelName || "".equals(channelName) || "null".equals(channelName) || "undefined".equals(channelName)){
            return JsonResp.fa("非法注册，请通过渠道注册！");
        }

        AppUser user=new AppUser();
        user.setProKey(proKey);
        ChannelCountLog channelCountLog=new ChannelCountLog();
        // 判断是否合法渠道
        EntityWrapper wrapper=new EntityWrapper();
        wrapper.eq("c_loginname",channelName);
        wrapper.eq("status",1);
        Channel channel=channelService.selectOne(wrapper);
        if(null==channel){
            return JsonResp.fa("未找到相应的渠道商");
        }
        channelCountLog.setChannelId(channel.getChannelId());
        user.setChannelId(channel.getChannelId());

        if (StringUtils.length(password) < 6) {
            return JsonResp.fa("密码长度请限制在6位以上！");
        }
        user.setAUphone(phone);
        user.setEquipmentFlag(equipmentFlag);
        user.setStatus(1);
        user.setPassword(Md5.md5Encode(password));
        UserCountLog userCountLog=new UserCountLog();
        Object userRecordId = request.getAttribute("userRecordId");
        if(userRecordId != null) {
            userCountLog.setDeviceFlag(userRecordId.toString());
            channelCountLog.setRecordId(Long.parseLong(userRecordId.toString()));
            user.setUserRecordId(Long.parseLong(userRecordId.toString()));
        }
        userService.insert(user);
        channelCountLog.setUserId(user.getAUid());
        channelCountLog.setRegisterNum(1);
        channelCountLog.setClientType(RequestUtil.getClientType(request));
        channelCountLogService.insert(channelCountLog);
        //插入日志表

        userCountLog.setRegisterNum(1);
        userCountLog.setPhone(phone);
        userCountLogService.insert(userCountLog);
        return JsonResp.ok("注册成功!");
    }

    /**
     * @注册用户 userId邀请注册用户id  手机 + 验证码注册 无密码
     */
    @RequestMapping(value = "/registerPhoneCodeV2", method = RequestMethod.GET)
    public JsonResp registerPhoneCodeV2(String phone,String code,  String channelName, int equipmentFlag,
                                        @RequestParam(required = false) String sourceBrowser,HttpServletRequest request) {
        String code1 = redisCache.getCache(phone);
        log.debug("通过渠道注册，phone :" + phone + " ,equipmentFlag : " + equipmentFlag  +  " , channelName " + channelName);

        if (!code.equals(code1)) {
            return JsonResp.fa("验证码错误");
        }

        AppUser user = new AppUser();
//        if(sourceBrowser !=null) {
//            if(sourceBrowser.equals("wx_browser") || sourceBrowser.equals("qq_browser")) {
//                user.setSourceBrowser(sourceBrowser);
//                log.info("注册来源:"+sourceBrowser);
//            }
//        }

        EntityWrapper<AppUser> ew = new EntityWrapper<AppUser>();
        ew.eq("a_uphone", phone);
        List<AppUser> users = userService.selectList(ew);
        if(users.size() > 0) {
            return JsonResp.fa("该号码已被注册！");
        }
        ChannelCountLog channelCountLog=new ChannelCountLog();

        user.setAUphone(phone);
        user.setEquipmentFlag(Integer.parseInt(RequestUtil.getClientType(request)));
        user.setStatus(1);

        //不是从渠道进来，防止撸贷
        if(null ==channelName || "".equals(channelName) || "null".equals(channelName) || "undefined".equals(channelName)){
            return JsonResp.fa("非法注册，请通过渠道注册！");
        }

        EntityWrapper<Channel> wrapper = new EntityWrapper<>();
        wrapper.eq("c_loginname", channelName);
        wrapper.eq("status", 1);
        Channel channel = channelService.selectOne(wrapper);
        if (channel == null) {
            return JsonResp.fa("未找到对应的渠道商或渠道已禁用");
        }
        channelCountLog.setChannelId(channel.getChannelId());
        user.setChannelId(channel.getChannelId());

        //标注渠道显示标志
        Integer aa = Integer.valueOf(channel.getProportion());
        Random r = new Random();
        int i = r.nextInt(100);
        if (i < aa) {
            user.setIsShow(1);
        }

        UserCountLog userCountLog=new UserCountLog();
        Object userRecordId = request.getAttribute("userRecordId");
        if(userRecordId != null) {
            user.setUserRecordId(Long.parseLong(userRecordId.toString()));
            channelCountLog.setRecordId(Long.parseLong(userRecordId.toString()));
            userCountLog.setDeviceFlag(userRecordId.toString());
        }
        userService.insert(user);
        channelCountLog.setUserId(user.getAUid());
        channelCountLog.setRegisterNum(1);
        channelCountLog.setClientType(RequestUtil.getClientType(request));
        channelCountLogService.insert(channelCountLog);
        //插入日志表
        userCountLog.setRegisterNum(1);
        userCountLog.setPhone(phone);
        userCountLogService.insert(userCountLog);
        return JsonResp.ok();

    }


    /**
     * @param
     * @return 返回值JsonResp
     * @忘记密码
     */
    @RequestMapping(value = "/loginPwdSetting", method = RequestMethod.GET)
    public JsonResp loginPwdSetting(String pwd, String code, String phone) {
        log.debug("设置登录密码,手机号："+ phone);
        String code1 = redisCache.getCache(phone);
        if (!code.equals(code1)) {
            return JsonResp.fa("验证码错误");
        }
        EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
        wrapper.eq("a_uphone", phone);
        AppUser user = userService.selectOne(wrapper);
        if (user != null) {
            user.setPassword(Md5.md5Encode(pwd));
            userService.updateById(user);
            return JsonResp.ok("修改成功");
        } else {
            return JsonResp.fa("您还未注册");
        }

    }

    // 忘记密码 重置密码 v2
    @RequestMapping(value = "/loginPwdSettingV2", method = RequestMethod.GET)
    public JsonResp loginPwdSettingV2(String pwd, String code, String phone) {
        log.info("忘记 重置 登录密码,手机号："+ phone);
        String code1 = redisCache.getCache(phone);
        if (!code.equals(code1)) {
            return JsonResp.fa("验证码错误");
        }

        if(null == pwd || pwd.length() == 0) {
            return JsonResp.fa("密码不能为空");
        }

        EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
        wrapper.eq("a_uphone", phone);
        AppUser user = userService.selectOne(wrapper);
        if (user != null) {
            user.setPassword(Md5.md5Encode(pwd));
            userService.updateById(user);
            return JsonResp.ok("修改密码成功");
        } else {
            if (user == null) {
                user = new AppUser();
                user.setAUphone(phone);
                user.setPassword(Md5.md5Encode(pwd));
                userService.insert(user);
                return JsonResp.ok("注册成功");
            }
        }
        return JsonResp.fa("error");
    }




    /**
     * @注销
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResp logout() {
        AppUser user = userService.findLoginUser();
        if (user != null) {
            log.debug("退出登陆:" + user.getAUphone());
        }
        userService.logout(user);
        return JsonResp.ok();
    }

    /**
     * 生成验证码图片
     *
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create/pictureCode", method = RequestMethod.GET)
    public JsonResp valicode(HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {

        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = ImageUtil.createImage();
        //将验证码code存入redis
        redisCache.putCacheWithExpireTime(session.getId() + "code", objs[0], 2 * 60);
        log.debug(session.getId());
        //将图片转化base传递给前端页面
        BufferedImage image = (BufferedImage) objs[1];
        String base64 = Base64Picture.putImage(image);
        Map<String, Object> map = new HashMap();
        map.put("base64", base64);
        return JsonResp.ok(map);
    }



    /**
     * @return 返回值JsonResp
     * @获取验证码
     */
    @RequestMapping(value = "/getPhoneCode", method = RequestMethod.GET)
    public JsonResp getPhoneCode(String phone, String picCode, HttpSession session) {
        log.debug("获取验证码");
        String code111 = redisCache.getCache(session.getId() + "code");
        if (redisCache.getCache(session.getId() + "code") == null || StringUtil.isEmpty(picCode) || !redisCache.getCache(session.getId() + "code").equals(picCode)) {
            return JsonResp.fa("图形验证码错误，请重新获取");
        }
        String code = SmsAPI.randomString(4);
        Map<String, String> para = new HashMap<>();
        para.put("code", code);
        messageService.send(phone, para, registerMsgId);
        redisCache.putCacheWithExpireTime(phone, code, 120);
        return JsonResp.ok(code);
    }


    /**
     * @return 返回值JsonResp
     * @获取验证码
     */
    @RequestMapping(value = "/getNewPhoneCode", method = RequestMethod.POST)
    public JsonResp getPhoneCode(@RequestBody CheckBody entity) {
        log.debug("获取验证码");
        Map<String,Object> params = XMLToMapToBean.BeanToMap(entity);
        String phone=entity.getPhone();
        String sign=entity.getSign();
        try {
            Boolean flag= WXParse.getReqSign(params,"obtaintheencryptedverificationcode").equals(sign)? true:false;
            if (flag){
                //如果需要验证码，可以先做验证
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("a_uphone", phone);
                AppUser appUser=userService.selectOne(entityWrapper);
                if(null!=appUser){
                    return JsonResp.fa("该手机号已经注册过，请直接登录");
                }
                Map<String, String> para = new HashMap<>();
                String code = SmsAPI.randomString(4);
                para.put("code", code);
                messageService.send(phone, para, registerMsgId);
                redisCache.putCacheWithExpireTime(phone, code, 120);
                return JsonResp.ok("短信验证码已发送，请注意查收");
            }else {
                 return   JsonResp.fa("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return JsonResp.fa("获取验证码失败");
    }

    @RequestMapping(value = "/getPhoneCodeV2", method = RequestMethod.GET)
    public JsonResp getPhoneCodeV2(String phone,String validateSign,HttpServletRequest request) {
        if(!ServletUtils.isMobile(phone)) {
            return JsonResp.fa("请输入正确格式的手机号！");
        }
        String validateStr = phone+"YDCODE9812#!1";
        if(!Md5.md5Encode(validateStr).equals(validateSign)) {
            return JsonResp.fa("非法请求");
        }
        String ip =RequestUtil.getIpAddr(request);
        //log.info("验证码 ip = "+ip);
        String url = request.getRequestURL().toString();
        String key = "daichao_limit_req_".concat(url).concat(ip);
        log.info("key =  "+key);
        //加1 值
        long count = redisTemplate.opsForValue().increment(key, 1);

        //允许访问的次数
        int limitCount = 2;

        //时间段(秒)，多少时间段内运行访问count次
        long limitTime  = 60;
        if (count == 1) {
            //设置1分钟过期
            redisTemplate.expire(key,limitTime, TimeUnit.SECONDS);
        }
        if (count > limitCount) {
            log.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limitTime + "]");
            throw new RuntimeException("操作太频繁，请稍后再试");
        }
        // 10 分支内同一手机号 3次 限制
        if(!isNeedCode(phone)){
            return  JsonResp.fa("操作太频繁，请稍后再试");
        }
        String code = SmsAPI.randomString(4);
        Map<String,String> para=new HashMap<>();
        para.put("code",code);
        messageService.send(phone,para,registerMsgId);
        redisCache.putCacheWithExpireTime(phone, code, 300);
        return JsonResp.ok("短信验证码已发送，请注意查收");
    }

    @RequestMapping(value = "/getRegisterCode", method = RequestMethod.GET)
    public JsonResp getRegisterCode(String phone,String validateSign,HttpServletRequest request) {
        if(!ServletUtils.isMobile(phone)) {
            return JsonResp.fa("请输入正确格式的手机号！");
        }
        String validateStr = phone+"YDCODE9812#!1";
        if(!Md5.md5Encode(validateStr).equals(validateSign)) {
            return JsonResp.fa("非法请求");
        }
        String ip =RequestUtil.getIpAddr(request);
        //log.info("验证码 ip = "+ip);
        String url = request.getRequestURL().toString();
        String key = "daichao_limit_req_".concat(url).concat(ip);
        log.info("key =  "+key);
        //加1 值
        long count = redisTemplate.opsForValue().increment(key, 1);

        //允许访问的次数
        int limitCount = 2;

        //时间段(秒)，多少时间段内运行访问count次
        long limitTime  = 60;
        if (count == 1) {
            //设置1分钟过期
            redisTemplate.expire(key,limitTime, TimeUnit.SECONDS);
        }
        if (count > limitCount) {
            log.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limitTime + "]");
            throw new RuntimeException("操作太频繁，请稍后再试");
        }
        // 10 分支内同一手机号 3次 限制
        if(!isNeedCode(phone)){
            return  JsonResp.fa("操作太频繁，请稍后再试");
        }
        //如果需要验证码，可以先做验证
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("a_uphone", phone);
        AppUser appUser=userService.selectOne(entityWrapper);
        if(null!=appUser){
            return JsonResp.fa("该手机号已经注册过，请直接登录");
        }
        String code = SmsAPI.randomString(4);
        Map<String,String> para=new HashMap<>();
        para.put("code",code);
        messageService.send(phone,para,registerMsgId);
        redisCache.putCacheWithExpireTime(phone, code, 300);
        return JsonResp.ok("短信验证码已发送，请注意查收");
    }


    /**
     * 如果10分钟内验证码获取次数超过3次，则需要现在图片验证码
     *
     * @param phone
     * @return
     */
    public boolean isNeedCode(String phone) {
        String rediskey = "daichao_"+phone + "count";
        if (!redisCache.haveCache(rediskey)) {
            redisCache.putCacheWithExpireTime(rediskey, 1, 600);
        }
        Integer redisVal = Integer.parseInt(redisCache.getCache(rediskey).toString());
        if (redisVal > 3) {
            return false;
        }
        redisCache.putCacheWithExpireTime(rediskey, redisVal + 1, 600);
        return true;
    }




//    public  String getRecordId(HttpServletRequest request){
//        String recordId="";
//        UserRecord userRecord=new UserRecord();
//        String  deviceFlag=request.getHeader("deviceFlag");
//        String ip = RequestUtil.getIpAddr(request);
//        String  type=request.getHeader("type");
//        //移动设备
//        if(null!=type&&!"".equals(type)){
//             userRecord=redisCache.getCache(deviceFlag+ip);
//            if(userRecord==null){
//                EntityWrapper entityWrapper=new EntityWrapper();
//                entityWrapper.eq("type",type);
//                entityWrapper.eq("udid",deviceFlag);
//                entityWrapper.eq("ip",ip);
//                userRecord=userRecordService.selectOne(entityWrapper);
//            }
//        }else {
//            String userAgent= request.getHeader("user-agent");
//             userRecord=redisCache.getCache(ip+userAgent);
//            if(userRecord==null){
//                EntityWrapper entityWrapper=new EntityWrapper();
//                entityWrapper.eq("user_agent",userAgent);
//                entityWrapper.eq("ip",ip);
//                userRecord= userRecordService.selectOne(entityWrapper);
//            }
//        }
//        recordId=String.valueOf(userRecord.getId());
//        return recordId;
//    }

}
