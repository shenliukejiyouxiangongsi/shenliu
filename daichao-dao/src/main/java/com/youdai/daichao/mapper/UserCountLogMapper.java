package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.UserCountLog;

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
public interface UserCountLogMapper extends BaseMapper<UserCountLog> {
	List<UserCountLog> selectUserCountList(Map<String, Object> map);

	List<UserCountLog> selectAllList(Map<String, Object> map);
}
