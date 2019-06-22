package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.MatchRecommend;
import com.youdai.daichao.domain.PdRecommend;
import com.youdai.daichao.mapper.MatchRecommendMapper;
import com.youdai.daichao.service.IMatchRecommendService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 匹配推荐表 服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-15
 */
@Service
public class MatchRecommendServiceImpl extends ServiceImpl<MatchRecommendMapper, MatchRecommend> implements IMatchRecommendService {

    @Autowired
    private  MatchRecommendMapper matchRecommendMapper;

    @Override
    public List<PdRecommend> selectMatchRecommendList(String pName) {
        return matchRecommendMapper.selectMatchRecommendList(pName);
    }

    @Override
    public int deleteReByIds(String ids) {
        String[] idss=ids.split(",");
        return matchRecommendMapper.deleteReByIds(idss);
    }

    @Override
    public PdRecommend selectOneByMrid(Integer mrid) {
        return matchRecommendMapper.selectOneByMrid(mrid);
    }
}
