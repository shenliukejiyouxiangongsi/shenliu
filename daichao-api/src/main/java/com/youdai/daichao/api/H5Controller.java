package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.domain.ChannelCountLog;
import com.youdai.daichao.service.IChannelCountLogService;
import com.youdai.daichao.service.IChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.util.calendar.BaseCalendar;

import java.util.Map;

@Controller
@Slf4j
public class H5Controller {
    @Autowired
    IChannelService channelService;
    @Autowired
    IChannelCountLogService channelCountLogService;

    @RequestMapping(value = "/h5")
    public String h5(String channelName) {
        ChannelCountLog channelCountLog = new ChannelCountLog();
        //判断是否是渠道商引流
        if (channelName != null && !"".equals(channelName) && !"null".equals(channelName) && !"undefined".equals(channelName)) {
            log.debug("存在channelName===================================================================" + channelName);
            EntityWrapper<Channel> wrapper = new EntityWrapper<>();
            wrapper.eq("c_loginname", channelName);
            wrapper.eq("status", 1);
            Channel channel = channelService.selectOne(wrapper);
            if(null != channel) {
                channelCountLog.setChannelId(channel.getChannelId());
                channelCountLog.setLoadPageNum(1);
                channelCountLogService.insert(channelCountLog);
                if("0".equals(channel.getType())) return "indexA";
                if("1".equals(channel.getType())) return "indexB";
                if("2".equals(channel.getType())) return "indexC";
            }
        }
        return "index";
    }

    @RequestMapping(value = "/H5web")
    public String h5web(Map map) {
        map.put("","");
        return "H5web";
    }

}
