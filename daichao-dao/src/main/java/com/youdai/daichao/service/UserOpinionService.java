package com.youdai.daichao.service;

import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.domain.UserOpinion;

import java.util.List;
import java.util.Map;

/**
 * @version : Ver 1.0
 * @UserOpinionService
 * @用户意见Service
 */
public interface UserOpinionService extends IService<UserOpinion> {

    List<UserOpinion> findAll(Map<String, Object> map);

    Integer findAllTotal(Map<String, Object> map);

    List<UserOpinion> selectUserOpinionList(UserOpinion userOpinion);
    int deleteUserOpinionByIds(String ids);
}
