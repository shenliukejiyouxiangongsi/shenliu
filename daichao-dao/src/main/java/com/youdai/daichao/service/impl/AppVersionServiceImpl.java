package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.AppVersion;
import com.youdai.daichao.mapper.AppVersionMapper;
import com.youdai.daichao.service.IAppVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * app版本表 服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements IAppVersionService {



    @Autowired
    private  AppVersionMapper appVersionMapper;

    @Override
    public int insertAppVerion(AppVersion appVersion){

        return appVersionMapper.insertAppVerion(appVersion);
    }

    @Override
    public int updateAppVerionById(AppVersion appVersion) {
        return appVersionMapper.updateAppVerionById(appVersion);
    }

    @Override
    public List<AppVersion> selectAppVersionList(AppVersion appVersion) {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    @Override
    public List<AppVersion> selectNewVersionList(AppVersion appVersion) {
        return appVersionMapper.selectNewVersionList(appVersion);
    }
}
