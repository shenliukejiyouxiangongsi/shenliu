package com.youdai.daichao.service;

import com.youdai.daichao.domain.AppVersion;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * app版本表 服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
public interface IAppVersionService extends IService<AppVersion> {
    int insertAppVerion(AppVersion appVersion);
    int updateAppVerionById(AppVersion appVersion);
    List<AppVersion> selectAppVersionList(AppVersion appVersion);

    List<AppVersion> selectNewVersionList(AppVersion appVersion);
}
