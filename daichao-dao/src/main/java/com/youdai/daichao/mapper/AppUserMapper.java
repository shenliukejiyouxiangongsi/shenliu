package com.youdai.daichao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.domain.AppUser;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface AppUserMapper extends BaseMapper<AppUser> {

    public List<AppUser> selectUserAndChannelNameList(AppUser appUser);
}
