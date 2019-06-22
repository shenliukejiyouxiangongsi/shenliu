package com.youdai.daichao.service;

import com.youdai.daichao.domain.AppSwitch;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
public interface IAppSwitchService extends IService<AppSwitch> {
    int changeStatus(AppSwitch appSwitch);
    List<AppSwitch>  selectAppSwitch(AppSwitch appSwitch);
}
