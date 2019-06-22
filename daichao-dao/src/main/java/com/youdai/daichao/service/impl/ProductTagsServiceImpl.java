package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.ProductTags;
import com.youdai.daichao.domain.ProductTags;
import com.youdai.daichao.mapper.ProductTagsMapper;
import com.youdai.daichao.service.IProductTagsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
@Service
public class ProductTagsServiceImpl extends ServiceImpl<ProductTagsMapper, ProductTags> implements IProductTagsService {

    @Autowired
    private ProductTagsMapper productTagsMapper;

    @Override
    public int deleteByIds(String ids) {
        String[] idss=ids.split(",");
        return productTagsMapper.deleteByIds(idss);
    }
}
