package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.ProductTags;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
public interface ProductTagsMapper extends BaseMapper<ProductTags> {
    int deleteByIds(String[] ids);
}
