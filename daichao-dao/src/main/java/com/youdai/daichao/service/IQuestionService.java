package com.youdai.daichao.service;

import com.youdai.daichao.domain.Question;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
public interface IQuestionService extends IService<Question> {
    int setDeleteByIds(String ids);
}
