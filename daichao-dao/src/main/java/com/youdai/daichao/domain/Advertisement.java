package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Data
@Accessors(chain = true)
public class Advertisement extends Model<Advertisement> {

    private static final long serialVersionUID = 1L;

    @TableId("ad_id")
    private int adId;
    @TableField("ad_url")
    private String adUrl;
    @TableField("jump_url")
    private String jumpUrl;
    private String describation;
    @TableField("status")
    private int status;
    private Integer sort;
    @TableField("create_time")
    private String createTime;
    @TableField("create_user")
    private String createUser;
    @TableField("update_time")
    private String updateTime;



    @Override
    protected Serializable pkVal() {
        return this.adId;
    }

}
