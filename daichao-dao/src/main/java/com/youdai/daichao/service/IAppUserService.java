package com.youdai.daichao.service;

import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.domain.AppUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface IAppUserService extends IService<AppUser> {

    AppUser loginByPhone(String phone, String password);

    void logout(AppUser user);

    AppUser findLoginUser();

    AppUser selectLoginUser();

    //手机验证码登录
    AppUser phoneCodeLogin(String phone, String code);

    //查询用户列表
    List<AppUser> selectUserAndChannelNameList(AppUser appUser);
}
