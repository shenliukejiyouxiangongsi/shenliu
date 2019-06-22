package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @version : Ver 1.0
 * @TextTemplate
 * @文本模板(text_template)
 */
@TableName("text_template")
public class TextTemplate implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * @备注:主键id
     * @字段:id BIGINT(19)
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * @备注:类型 ：1,帮助中心 2,协议 3,短信 4,消息推送模板 5,关于我们
     * @字段:type INT(10)
     */
    private Integer type;


    /**
     * @备注:标题
     * @字段:title VARCHAR(55)
     */
    private String title;


    /**
     * @备注:内容
     * @字段:text TEXT(65535)
     */
    private String text;


    /**
     * @备注:状态：1,正常 2, 删除
     * @字段:status INT(10)
     */
    private Integer status;
    @TableField("img_url")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public TextTemplate() {
    }

    public TextTemplate(
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


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
