package com.youdai.daichao.project.biz.channel.service;

import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.project.biz.channel.domain.ChannelCountLog;


import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-14
 */
public interface IChannelCountLogService extends IService<ChannelCountLog> {
	List<ChannelCountLog> selectChannelCountList(Map<String, Object> map);
}
