package com.youdai.daichao.api;

import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.MessageService;
import com.youdai.daichao.sms.SmsAPI;
import com.youdai.daichao.util.Base64Picture;
import com.youdai.daichao.util.ImageUtil;
import com.youdai.daichao.util.SerializerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/15
 * @Description
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/userChange")
public class LoginController {

    @Autowired
    IAppUserService userService;
    @Autowired
    RedisCache redisCache;


    /**修改登陆密码
     * @param
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "modifyPassword")
    public JsonResp modifyPassword(String oldPassword,String newPassword,HttpServletRequest request){
        if (StringUtils.length(newPassword) < 6) {
            return JsonResp.fa("新密码长度请限制在6位以上！");
        }
        String key=request.getHeader("token");
        AppUser user= (AppUser) SerializerUtil.deserializeObj(redisCache.getCache(key));
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            userService.updateById(user);
            redisCache.putCacheWithExpireTime(key,user,60*30);
            return JsonResp.ok("修改成功",key);
        }
        return JsonResp.fa("请检查您输入的旧密码是否正确");
    }
}
