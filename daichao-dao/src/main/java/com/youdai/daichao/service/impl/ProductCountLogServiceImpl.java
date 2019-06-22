package com.youdai.daichao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.domain.LandingPage;
import com.youdai.daichao.domain.ProductCountLog;
import com.youdai.daichao.mapper.ProductCountLogMapper;
import com.youdai.daichao.service.IProductCountLogService;

@Service
public class ProductCountLogServiceImpl extends ServiceImpl<BaseMapper<ProductCountLog>, ProductCountLog> implements IProductCountLogService{

	@Autowired
	private ProductCountLogMapper productCountLogMapper;

	
	@Override
	public List<LandingPage> selectProdctCountList(Map<String, Object> map) {
		
		return productCountLogMapper.selectProdctCountList(map);
	}

	@Override
	public List<LandingPage> selectListByDeviceFlag(Map<String, Object> map) {
		return productCountLogMapper.selectListByDeviceFlag(map);
	}

}
