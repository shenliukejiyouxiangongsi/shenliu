package com.youdai.daichao.service;

import com.youdai.daichao.domain.LandingPage;
import com.youdai.daichao.domain.ProductCountLog;

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
public interface IProductCountLogService extends IService<ProductCountLog> {
	List<LandingPage> selectProdctCountList(Map<String, Object> map);
	List<LandingPage> selectListByDeviceFlag(Map<String, Object> map);
}
