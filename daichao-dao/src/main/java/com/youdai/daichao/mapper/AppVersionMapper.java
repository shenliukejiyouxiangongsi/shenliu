package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.AppVersion;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * app版本表 Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
public interface AppVersionMapper extends BaseMapper<AppVersion> {
    int insertAppVerion(AppVersion appVersion);
    int updateAppVerionById(AppVersion appVersion);
    List<AppVersion> selectAppVersionList(AppVersion appVersion);

    List<AppVersion> selectNewVersionList(AppVersion appVersion);
}
