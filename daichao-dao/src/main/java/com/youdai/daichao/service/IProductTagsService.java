package com.youdai.daichao.service;

import com.youdai.daichao.domain.ProductTags;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
public interface IProductTagsService extends IService<ProductTags> {
    int deleteByIds(String ids);
}
