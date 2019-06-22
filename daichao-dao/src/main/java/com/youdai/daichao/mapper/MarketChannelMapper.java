package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.MarketChannel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-04-01
 */
public interface MarketChannelMapper extends BaseMapper<MarketChannel> {
    int deleteMarketByIds(String[] ids);
}
