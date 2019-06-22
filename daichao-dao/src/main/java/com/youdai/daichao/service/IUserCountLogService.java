package com.youdai.daichao.service;

import com.youdai.daichao.domain.UserCountLog;

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
public interface IUserCountLogService extends IService<UserCountLog> {
	List<UserCountLog> selectUserCountList(Map<String, Object> map);
}
