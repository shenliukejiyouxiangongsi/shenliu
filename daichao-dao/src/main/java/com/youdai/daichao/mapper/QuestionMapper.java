package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.Question;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
public interface QuestionMapper extends BaseMapper<Question> {
    int updateIsDeleteByIds(String[] idss);
}
