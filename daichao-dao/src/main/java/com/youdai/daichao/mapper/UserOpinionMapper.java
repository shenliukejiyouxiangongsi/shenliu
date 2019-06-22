package com.youdai.daichao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.domain.UserOpinion;

import java.util.List;
import java.util.Map;

/**
 * @version : Ver 1.0
 * @UserOpinionMapper
 * @用户意见Mapper
 */
public interface UserOpinionMapper extends BaseMapper<UserOpinion> {

    List<UserOpinion> findAll(Map<String, Object> map);

    Integer findAllTotal(Map<String, Object> map);

    List<UserOpinion> selectUserOpinionList(UserOpinion userOpinion);

    /**
     * 批量删除用户反馈
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserOpinionByIds(String[] ids);

}
