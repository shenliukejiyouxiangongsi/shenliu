package com.youdai.daichao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.domain.PdRecommend;
import com.youdai.daichao.domain.Recommend;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface RecommendMapper extends BaseMapper<Recommend> {
	List<PdRecommend> selectRecommendList(@Param("pName") String pName);
	PdRecommend selectOneByRid(int rId);
	int  deleteReByIds(String[] ids);
	List<PdRecommend> selectRecommendByType(@Param("rType") String rType);
	List<PdRecommend> selectTopic(@Param("rType") String rType);

}
