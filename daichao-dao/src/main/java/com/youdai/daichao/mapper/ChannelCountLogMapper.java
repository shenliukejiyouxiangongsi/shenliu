package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.ChannelCount;
import com.youdai.daichao.domain.ChannelCountLog;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-14
 */
public interface ChannelCountLogMapper extends BaseMapper<ChannelCountLog> {
	List<ChannelCountLog> selectChannelCountList(Map<String, Object> map);
}
