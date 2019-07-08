package com.youdai.daichao.project.system.user.service;

import com.youdai.daichao.project.system.user.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务层
 * 
 * @author zhuliangjie
 */
public interface SysUserService
{


    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    public SysUser selectUserByid(Long channelId);

    public int resetUserPwd(SysUser user);
}
