package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @version : Ver 1.0
 * @MsgModel
 * @(msg_model)
 */
@TableName("msg_model")
public class MsgModel implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * @备注:
     * @字段:id BIGINT(19)
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * @备注:内容
     * @字段:content MEDIUMTEXT(16777215)
     */
    private String content;

    /**
     * @备注:内容
     * @字段:content MEDIUMTEXT(16777215)
     */
    @TableField("tp_id")
    private String tpId;

    private Integer status;


    public MsgModel() {
    }

    public MsgModel(
            Long id
    ) {
        this.id = id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }


    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
