package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.Channel;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface ChannelMapper extends BaseMapper<Channel> {
	List<Channel> selectChannelList(Channel channel);
	int addChannel(Channel channel);
	int updateChannel(Channel channel);
	int deleteChannelByIds(String[] ids);
	int changeStatus(Channel channel);
	List<Channel> selectAllChannel();
}
