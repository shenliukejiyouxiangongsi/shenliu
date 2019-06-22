package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.Merchant;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-05-18
 */
public interface MerchantMapper extends BaseMapper<Merchant> {
    int changeStatus(Map<String, Object> map);
}
