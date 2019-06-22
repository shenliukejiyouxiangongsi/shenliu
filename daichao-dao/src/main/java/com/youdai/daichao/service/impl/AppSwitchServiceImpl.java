package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.AppSwitch;
import com.youdai.daichao.mapper.AppSwitchMapper;
import com.youdai.daichao.service.IAppSwitchService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
@Service
public class AppSwitchServiceImpl extends ServiceImpl<AppSwitchMapper, AppSwitch> implements IAppSwitchService {

    @Autowired
    private AppSwitchMapper appSwitchMapper;

    @Override
    public int changeStatus(AppSwitch appSwitch) {
        return appSwitchMapper.changeStatus(appSwitch);
    }

    @Override
    public List<AppSwitch> selectAppSwitch(AppSwitch appSwitch) {
        return appSwitchMapper.selectAppSwitch(appSwitch);
    }
}
