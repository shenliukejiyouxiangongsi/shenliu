package com.youdai.daichao.project.biz.channel.domain;

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
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-03-14
 */
@Data
@Accessors(chain = true)
@TableName("channel_count_log")
public class ChannelCountLog extends Model<ChannelCountLog> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private int id;
    /**
     * 渠道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 落地页pv
     */
    @TableField("load_page_num")
    private Integer loadPageNum;
    /**
     * 落地页uv
     */
    @TableField("view_page_num")
    private Integer viewPageNum;
    /**
     * 实际注册数
     */
    @TableField("register_num")
    private Integer registerNum;
    /**
     * 扣量注册数
     */
    @TableField("discount_num")
    private Integer discountNum;
    /**
     * 登陆数
     */
    @TableField("login_num")
    private Integer loginNum;
    /**
     * 记录表id
     */
    @TableField("record_id")
    private Long recordId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }



}
