package com.youdai.daichao.project.system.user.service;


import com.youdai.daichao.framework.shiro.service.PasswordService;

import com.youdai.daichao.project.system.user.domain.SysUser;

import com.youdai.daichao.project.system.user.mapper.SysUserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 用户 业务层处理
 * 
 * @author zhuliangjie
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService
{
    @Autowired
    private SysUserMapper userMapper;



    @Autowired
    private  PasswordService passwordService;



    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    @Override
    public SysUser selectUserByid(Long channelId) {
        return userMapper.selectUserByid(channelId);
    }

    @Override
    public int resetUserPwd(SysUser user) {
        user.setPassword(passwordService.encryptPassword(user.getUserName(), user.getPassword()));

        return userMapper.resetUserPwd(user);
    }


}
