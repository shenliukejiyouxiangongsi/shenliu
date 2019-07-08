package com.youdai.daichao.project.biz.channel.mapper;



import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.project.biz.channel.domain.ChannelCountLog;

import java.util.List;
import java.util.Map;

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
