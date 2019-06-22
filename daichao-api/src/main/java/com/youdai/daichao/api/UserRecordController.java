package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.domain.ChannelCountLog;
import com.youdai.daichao.domain.UserRecord;
import com.youdai.daichao.resultcommon.JsonResp;
import com.youdai.daichao.service.IChannelCountLogService;
import com.youdai.daichao.service.IChannelService;
import com.youdai.daichao.service.IUserRecordService;
import com.youdai.daichao.util.DateUtils;
import com.youdai.daichao.util.RequestUtil;
import com.youdai.daichao.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/19
 * @Description   用户来源信息记录
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/userRecord")
public class UserRecordController {

    @Autowired
    IUserRecordService userRecordService;
    @Autowired
    IChannelService channelService;
    @Autowired
    IChannelCountLogService channelCountLogService;

    @RequestMapping(value = "put",method = RequestMethod.GET)
    public JsonResp getUser(String ip , String channelName, HttpServletRequest request){
        log.info("进入put方法，ip="+ip);
        if(StringUtils.isEmpty(ip)){
            ip = RequestUtil.getIpAddr(request);
        }
        String userAgent= request.getHeader("user-agent");
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("ip",ip);
        entityWrapper.eq("user_agent",userAgent);
        ChannelCountLog channelCountLog=new ChannelCountLog();
        channelCountLog.setLoadPageNum(1);
        if(null!=channelName){
            EntityWrapper wrapper=new EntityWrapper();
            wrapper.eq("c_loginname",channelName);
            wrapper.eq("status",1);
            Channel channel=channelService.selectOne(wrapper);
            if (null==channel){
                return JsonResp.fa("渠道被禁用");
            }
            channelCountLog.setChannelId(channel.getChannelId());
            entityWrapper.eq("channel_id",channel.getChannelId());
            UserRecord userRecord= userRecordService.selectOne(entityWrapper);
            if (null==userRecord){
                //ip相同浏览器不同
                channelCountLog.setViewPageNum(1);
                channelCountLog.setEveryViewPageNum(1);
                //插入记录表
                UserRecord record=new UserRecord();
                record.setIp(ip);
                record.setUserAgent(userAgent);
                record.setChannelId(channel.getChannelId());
                userRecordService.insert(record);
                channelCountLog.setRecordId(record.getId());
            }else {
                EntityWrapper channelWrapper=new EntityWrapper();
                channelWrapper.eq("record_id",userRecord.getId());
                channelWrapper.gt("create_time", DateUtils.getDayStartDate(new Date()));
                ChannelCountLog channelCountLog1=channelCountLogService.selectOne(channelWrapper);
                if(null==channelCountLog1){
                    channelCountLog.setEveryViewPageNum(1);
                }
                channelCountLog.setRecordId(userRecord.getId());
            }
        }
        channelCountLogService.insert(channelCountLog);
        return JsonResp.ok("成功");
    }



}
