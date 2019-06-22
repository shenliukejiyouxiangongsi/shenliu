package com.youdai.daichao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.common.support.Convert;
import com.youdai.daichao.domain.UserOpinion;
import com.youdai.daichao.mapper.UserOpinionMapper;
import com.youdai.daichao.service.UserOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version : Ver 1.0
 * @UserOpinionServiceImpl
 * @用户意见ServiceImpl
 */
@Service
public class UserOpinionServiceImpl extends ServiceImpl<UserOpinionMapper, UserOpinion> implements UserOpinionService {

    @Autowired
    private UserOpinionMapper userOpinionMapper;

    public List<UserOpinion> findAll(Map<String, Object> map) {

        return userOpinionMapper.findAll(map);
    }

    public Integer findAllTotal(Map<String, Object> map) {
        return userOpinionMapper.findAllTotal(map);
    }

    @Override
    public List<UserOpinion> selectUserOpinionList(UserOpinion userOpinion)
    {
        return userOpinionMapper.selectUserOpinionList(userOpinion);
    }


    @Override
    public int deleteUserOpinionByIds(String ids)
    {
        return userOpinionMapper.deleteUserOpinionByIds(Convert.toStrArray(ids));
    }
}
