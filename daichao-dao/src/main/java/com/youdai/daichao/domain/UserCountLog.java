package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-03-14
 */
@Data
@Accessors(chain = true)
@TableName("user_count_log")
public class UserCountLog extends Model<UserCountLog> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private int id;
    /**
     * 用户注册数
     */
    @TableField("register_num")
    private Integer registerNum;
    /**
     * 登陆app数
     */
    @TableField("login_num")
    private Integer loginNum;
    /**
     * 贷款产品pv
     */
    @TableField("view_product_num")
    private Integer viewProductNum;
    /**
     * 用户id
     */
    @TableField("device_flag")
    private String deviceFlag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    @TableField(exist = false)
    private  Integer channelRegisterNum;
    @TableField(exist = false)
    private String channelName;
    private String equipmentFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
