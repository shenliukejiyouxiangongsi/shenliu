package com.youdai.daichao.project.system.user.mapper;

import com.youdai.daichao.project.system.user.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户表 数据层
 * 
 * @author zhuliangjie
 */
@Mapper
public interface SysUserMapper
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
