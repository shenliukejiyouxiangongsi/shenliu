package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.AppSwitch;
import com.youdai.daichao.domain.MarketChannel;
import com.youdai.daichao.mapper.AppSwitchMapper;
import com.youdai.daichao.mapper.MarketChannelMapper;
import com.youdai.daichao.service.IMarketChannelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-04-01
 */
@Service
public class MarketChannelServiceImpl extends ServiceImpl<MarketChannelMapper, MarketChannel> implements IMarketChannelService {

    @Autowired
    private MarketChannelMapper marketChannelMapper;

    @Autowired
    private AppSwitchMapper  appSwitchMapper;

    @Override
    @Transactional
    public int deleteMarketByIds(String ids) {
        String[] idss=ids.split(",");
        for (String id:idss) {
            Integer marketId=Integer.parseInt(id);
            appSwitchMapper.deleteByMarketId(marketId);
        }
        return marketChannelMapper.deleteMarketByIds(idss);
    }

}
