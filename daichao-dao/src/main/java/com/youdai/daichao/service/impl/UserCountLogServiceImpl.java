package com.youdai.daichao.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.domain.UserCountLog;
import com.youdai.daichao.mapper.UserCountLogMapper;
import com.youdai.daichao.service.IUserCountLogService;

@Service
public class UserCountLogServiceImpl extends ServiceImpl<BaseMapper<UserCountLog>, UserCountLog> implements IUserCountLogService{

	@Autowired 
	private UserCountLogMapper userCountLogMapper;
	
	@Override
	public List<UserCountLog> selectUserCountList(Map<String, Object> map) {
		
		return userCountLogMapper.selectUserCountList(map);
	}

	@Override
	public List<UserCountLog> selectAllList(Map<String, Object> map) {

		return userCountLogMapper.selectAllList(map);
	}

}
