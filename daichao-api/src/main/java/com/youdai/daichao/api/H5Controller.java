package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.common.enums.ChannelStatusEnum;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.domain.ChannelCountLog;
import com.youdai.daichao.domain.UserRecord;
import com.youdai.daichao.service.IChannelCountLogService;
import com.youdai.daichao.service.IChannelService;
import com.youdai.daichao.service.IUserRecordService;
import com.youdai.daichao.util.DateUtils;
import com.youdai.daichao.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.util.calendar.BaseCalendar;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@Slf4j
public class H5Controller {
    @Autowired
    IChannelService channelService;
    @Autowired
    IChannelCountLogService channelCountLogService;
    @Autowired
    IUserRecordService userRecordService;
    @RequestMapping(value = "/h5")
    public String h5(String channelName, HttpServletRequest request) {
        if (channelName == null || "".equals(channelName) || "null".equals(channelName) || "undefined".equals(channelName))
            return "error";

        //判断是否是渠道商引流
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("c_loginname", channelName);
        wrapper.eq("status", ChannelStatusEnum.ONLINE.getStatus());
        Channel channel = channelService.selectOne(wrapper);
        //错误的渠道
        if(null == channel) return "error";

        wrapper = new EntityWrapper<>();
        wrapper.eq("user_agent",request.getHeader("user-agent"));
        wrapper.eq("ip",RequestUtil.getIpAddr(request));
        wrapper.eq("channel_id",channel.getChannelId());
        UserRecord userRecord = userRecordService.selectOne(wrapper);

        if(null == userRecord) return "error";
        wrapper = new EntityWrapper();
        wrapper.eq("record_id",userRecord.getId());
        wrapper.gt("create_time", DateUtils.getDayStartDate(new Date()));
        ChannelCountLog channelCountLog=channelCountLogService.selectOne(wrapper);
        //今天没有记录 则 uv +1
        if(null == channelCountLog) {
            channelCountLog = new ChannelCountLog();
            channelCountLog.setViewPageNum(1);
            channelCountLog.setEveryViewPageNum(1);
        }else {
            channelCountLog = new ChannelCountLog();
        }

        channelCountLog.setRecordId(userRecord.getId());
        channelCountLog.setChannelId(channel.getChannelId());
        channelCountLog.setLoadPageNum(1);
        channelCountLog.setClientType(RequestUtil.getClientType(request));
        channelCountLogService.insert(channelCountLog);

        //跳转不同风格h5
        if("0".equals(channel.getType())) return "indexA";
        if("1".equals(channel.getType())) return "indexB";
        if("2".equals(channel.getType())) return "indexC";

        return "index";
    }

    @RequestMapping(value = "/H5web")
    public String h5web(Map map) {
        map.put("","");
        return "H5web";
    }

}
