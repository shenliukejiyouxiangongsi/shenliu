package com.youdai.daichao.service;

import com.youdai.daichao.domain.Channel;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface IChannelService extends IService<Channel> {
	 List<Channel> selectChannelList(Channel channel);
	 int updateChannel(Channel channel);
	 int deleteChannelByIds(String ids);
	 int changeStatus(Channel channel);
	 List<Channel> selectAllChannel();

	 int addChannel(Channel channel);
}
