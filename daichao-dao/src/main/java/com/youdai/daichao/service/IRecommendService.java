package com.youdai.daichao.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.domain.PdRecommend;
import com.youdai.daichao.domain.Recommend;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface IRecommendService extends IService<Recommend> {
	List<PdRecommend> selectRecommendList(String pname);
	PdRecommend selectOneByRid(int rId);
	int  deleteReByIds(String ids);
}
