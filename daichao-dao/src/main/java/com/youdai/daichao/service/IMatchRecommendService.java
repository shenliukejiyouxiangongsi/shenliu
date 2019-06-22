package com.youdai.daichao.service;

import com.youdai.daichao.domain.MatchRecommend;
import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.domain.PdRecommend;

import java.util.List;

/**
 * <p>
 * 匹配推荐表 服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-15
 */
public interface IMatchRecommendService extends IService<MatchRecommend> {
    List<PdRecommend> selectMatchRecommendList(String pName);
    int deleteReByIds(String ids);
    PdRecommend selectOneByMrid(Integer mrid);
}
