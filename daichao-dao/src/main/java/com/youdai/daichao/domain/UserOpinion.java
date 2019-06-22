package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @version : Ver 1.0
 * @UserOpinion
 * @用户意见(user_opinion)
 */
@TableName("user_opinion")
public class UserOpinion implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * @备注:主键id
     * @字段:id BIGINT(19)
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * @备注:
     * @字段:user_id BIGINT(19)
     */
    @TableField("user_id")
    private Long userId;


    /**
     * @备注:用户账号
     * @字段:user_phone BIGINT(19)
     */
    @TableField("user_phone")
    private String userPhone;


    /**
     * @备注:类型：1,认证 2, 回收 3,回购 4,体验与界面 5,其他
     * @字段:type INT(10)
     */
    private Integer type;


    /**
     * @备注:内容
     * @字段:text TEXT(65535)
     */
    private String text;


    /**
     * @备注:图片(多张逗号分隔)
     * @字段:picture VARCHAR(255)
     */
    private String picture;


    /**
     * @备注:状态：1,正常 2, 删除
     * @字段:status INT(10)
     */
    private Integer status;

    /**
     * @创建时间
     */
    @TableField("create_time")
    private Date createTime;


    @TableField(exist = false)
    private AppUser user;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public UserOpinion() {
    }

    public UserOpinion(
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


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }


    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhone() {
        return this.userPhone;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return this.picture;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
}
