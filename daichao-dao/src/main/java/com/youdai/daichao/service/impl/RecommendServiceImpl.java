package com.youdai.daichao.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.domain.PdRecommend;
import com.youdai.daichao.domain.Recommend;
import com.youdai.daichao.mapper.RecommendMapper;
import com.youdai.daichao.service.IRecommendService;

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
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements IRecommendService {

	@Autowired
	private RecommendMapper recommendMapper;
	
	@Override
	public List<PdRecommend> selectRecommendList(String pName) {
		
		return recommendMapper.selectRecommendList(pName);
	}

	@Override
	public PdRecommend selectOneByRid(int rId) {
		
		return recommendMapper.selectOneByRid(rId);
	}

	@Override
	public int deleteReByIds(String ids) {
		String[] idss = ids.split(",");
		return recommendMapper.deleteReByIds(idss);
	}

	@Override
	public List<PdRecommend> selectRecommendByType(String rtype) {
		return recommendMapper.selectRecommendByType(rtype);
	}

	public List<PdRecommend> selectTopic(String rType){
		return recommendMapper.selectTopic(rType);
	}
}
