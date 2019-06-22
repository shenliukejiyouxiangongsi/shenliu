package com.youdai.daichao.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zhankui
 * @since 2019-03-19
 */
@Data
@Accessors(chain = true)
@TableName("user_record")
public class UserRecord extends Model<UserRecord> {


    private static final long serialVersionUID = 1L;


    /**
     * @备注:主键id
     * @字段:id BIGINT(19)
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 移动端设备类型
     */
    private Integer type;
    /**
     * 移动的设备唯一标识
     */
    private String udid;
    /**
     * ip地址
     */
    private String ip;
    /**
     * ua信息
     */
    @TableField("user_agent")
    private String userAgent;
    /**
     * 浏览器类型
     */
    @TableField("browser")
    private String browser;
    /**
     * 通道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;


    @TableField(exist = false)
    private String channelName;

    /**
     * 打开APP时间
     */
    @TableField(exist = false)
    private String openTime;

    /**
     * 浏览次数
     */
    @TableField(exist = false)
    private Long viewCount;

    /**
     * 申请次数
     */
    @TableField(exist = false)
    private Long applicationNumber;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
