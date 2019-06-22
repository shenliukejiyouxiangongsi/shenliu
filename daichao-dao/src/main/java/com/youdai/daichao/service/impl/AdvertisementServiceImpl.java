package com.youdai.daichao.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.domain.Advertisement;
import com.youdai.daichao.mapper.AdvertisementMapper;
import com.youdai.daichao.service.IAdvertisementService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Service
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementMapper, Advertisement> implements IAdvertisementService {

	@Autowired
	private  AdvertisementMapper advertisementMapper;
	
	@Override
	public List<Advertisement> selectAdvertisementList(Advertisement advertisement) {
		
		return advertisementMapper.selectAdvertisementList(advertisement);
	}

	@Override
	public int deleteAdByIds(String ids) {
		String[] idss = ids.split(",");
		return advertisementMapper.deleteAdByIds(idss);
	}

}
