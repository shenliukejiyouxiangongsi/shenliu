package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.LandingPage;
import com.youdai.daichao.domain.ProductCountLog;

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
public interface ProductCountLogMapper extends BaseMapper<ProductCountLog> {
	List<LandingPage> selectProdctCountList(Map<String, Object> map);
	List<LandingPage> selectListByDeviceFlag(Map<String, Object> map);
}
