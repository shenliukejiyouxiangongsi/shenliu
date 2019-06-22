package com.youdai.daichao.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.domain.Advertisement;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface IAdvertisementService extends IService<Advertisement> {
	
	List<Advertisement> selectAdvertisementList(Advertisement advertisement);
	int deleteAdByIds(String ids);
}
