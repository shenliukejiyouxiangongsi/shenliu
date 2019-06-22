package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@ToString
public class Channel extends Model<Channel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "channel_id",type = IdType.AUTO)
    private int channelId;
    @TableField("channel_name")
    private String channelName;
    @TableField("c_loginname")
    private String cLoginname;
    @TableField("c_url")
    private String cUrl;
    private String proportion;
    @TableField("c_password")
    private String cPassword;
    @TableField("create_time")
    private String createTime;
    @TableField("create_user")
    private String createUser;
    @TableField("update_time")
    private String updateTime;
    private Integer status;
    @TableField("clear_form")
    private String clearForm;
    @TableField("price")
    private BigDecimal price;
    @Override
    protected Serializable pkVal() {
        return this.channelId;
    }

}
