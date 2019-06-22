package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.AppSwitch;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
public interface AppSwitchMapper extends BaseMapper<AppSwitch> {
    int changeStatus(AppSwitch appSwitch);
    List<AppSwitch> selectAppSwitch(AppSwitch appSwitch);

    int deleteByMarketId(Integer marketId);
}
