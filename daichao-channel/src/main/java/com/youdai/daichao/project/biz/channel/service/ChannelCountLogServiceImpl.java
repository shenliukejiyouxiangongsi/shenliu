package com.youdai.daichao.project.biz.channel.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.youdai.daichao.project.biz.channel.domain.ChannelCountLog;
import com.youdai.daichao.project.biz.channel.mapper.ChannelCountLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-14
 */
@Service
public class ChannelCountLogServiceImpl extends ServiceImpl<ChannelCountLogMapper, ChannelCountLog> implements IChannelCountLogService {


	@Autowired
	private ChannelCountLogMapper channelCountLogMapper;
	
	@Override
	public List<ChannelCountLog> selectChannelCountList(Map<String, Object> map) {
		
		return channelCountLogMapper.selectChannelCountList(map);
	}


}
