package com.youdai.daichao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.domain.Advertisement;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface AdvertisementMapper extends BaseMapper<Advertisement> {
	List<Advertisement> selectAdvertisementList(Advertisement advertisement);
	int  deleteAdByIds(String[] ids);
}
