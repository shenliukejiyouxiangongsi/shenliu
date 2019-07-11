package com.youdai.daichao.service;

import com.youdai.daichao.domain.ChannelCount;
import com.youdai.daichao.domain.ChannelCountLog;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

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
	List<ChannelCountLog> selectChannelCountListGroupychannel(Map<String, Object> map);

}
