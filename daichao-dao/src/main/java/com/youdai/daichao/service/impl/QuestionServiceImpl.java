package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.Question;
import com.youdai.daichao.mapper.QuestionMapper;
import com.youdai.daichao.service.IQuestionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public int setDeleteByIds(String ids) {
        String[] idss=ids.split(",");
        return questionMapper.updateIsDeleteByIds(idss);
    }
}
