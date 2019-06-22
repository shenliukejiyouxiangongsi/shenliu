package com.youdai.daichao.service.impl;


import com.youdai.daichao.common.constant.Constants;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.mapper.ChannelMapper;
import com.youdai.daichao.service.IChannelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {
	
	@Autowired
	private ChannelMapper channelMapper;



	@Override
	public List<Channel> selectChannelList(Channel channel) {
		
		return channelMapper.selectChannelList(channel);
	}

	@Override
	public int updateChannel(Channel channel) {

		return channelMapper.updateChannel(channel);
	}

	@Override
	public int deleteChannelByIds(String ids) {
		String[] idss = ids.split(",");
		return channelMapper.deleteChannelByIds(idss);
	}

	@Override
	public int changeStatus(Channel channel) {
		
		return channelMapper.changeStatus(channel);
	}

	@Override
	public List<Channel> selectAllChannel() {
		
		return channelMapper.selectAllChannel();
	}
	 /**
	  * 添加渠道 路径用渠道id
	  */
	@Override
	@Transactional
	public int addChannel(Channel channel) {
		return channelMapper.updateById(channel);
	}
	
}
