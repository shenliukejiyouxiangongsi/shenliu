package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
@Data
@Accessors(chain = true)
public class Question extends Model<Question> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "question_id", type = IdType.AUTO)
    private Long questionId;
    /**
     * 问题描述
     */
    private String description;
    /**
     * 选项A
     */
    @TableField("choose_answerA")
    private String chooseAnswera;
    /**
     * 选项B
     */
    @TableField("choose_answerB")
    private String chooseAnswerb;
    /**
     * 选项C
     */
    @TableField("choose_answerC")
    private String chooseAnswerc;
    /**
     * 选项D
     */
    @TableField("choose_answerD")
    private String chooseAnswerd;
    /**
     * 正确答案 备用(目前需求 没有正确答案 选每一项都合理)
     */
    @TableField(value = "real_answer",strategy = FieldStrategy.IGNORED)
    private String realAnswer;
    /**
     * 等级（0,1,2,3）
     */
    private Integer degree;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;
    @TableField("create_user_id")
    private Long createUserId;
    @TableField("modify_user_id")
    private Long modifyUserId;
    /**
     * 0正常  1删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 排序
     * @return
     */
    @TableField("question_sort")
    private Integer questionSort;

    @TableField("score_A")
    private Integer scoreA;

    @TableField("score_B")
    private Integer scoreB;


    @TableField("score_C")
    private Integer scoreC;


    @TableField("score_D")
    private Integer scoreD;

    @Override
    protected Serializable pkVal() {
        return this.questionId;
    }

}
