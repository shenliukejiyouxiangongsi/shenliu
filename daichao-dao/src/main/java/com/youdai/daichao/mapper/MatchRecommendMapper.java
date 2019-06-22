package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.MatchRecommend;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.domain.PdRecommend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 匹配推荐表 Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-05-15
 */
public interface MatchRecommendMapper extends BaseMapper<MatchRecommend> {
    List<PdRecommend> selectMatchRecommendList(@Param("pName") String pName);

    int deleteReByIds(String[] ids);

    PdRecommend selectOneByMrid(Integer mrId);
}
