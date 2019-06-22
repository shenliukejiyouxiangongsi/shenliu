package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 答题结果
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
@Data
@Accessors(chain = true)
@TableName("question_answer_user")
public class QuestionAnswerUser extends Model<QuestionAnswerUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 问题id
     */
    @TableId("question_id")
    private Long questionId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 答题选项
     */
    @TableField("answer_choose")
    private String answerChoose;
    /**
     * 答题时间
     */
    @TableField("answer_time")
    private Date answerTime;
    /**
     * 答题是否正确（目前阶段不处理  无正确答案）
     */
    @TableField("is_real")
    private Long isReal;
    /**
     * 0正常  1删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private int score;


    @Override
    protected Serializable pkVal() {
        return this.questionId;
    }

}
