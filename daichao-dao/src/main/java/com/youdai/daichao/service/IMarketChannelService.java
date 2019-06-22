package com.youdai.daichao.service;

import com.youdai.daichao.domain.MarketChannel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-04-01
 */
public interface IMarketChannelService extends IService<MarketChannel> {
    int deleteMarketByIds(String ids);
}
