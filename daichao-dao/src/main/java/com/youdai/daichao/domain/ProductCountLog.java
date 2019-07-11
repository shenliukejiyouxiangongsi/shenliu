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
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-03-14
 */
@Data
@Accessors(chain = true)
@TableName("product_count_log")
public class ProductCountLog extends Model<ProductCountLog> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private int id;
    /**
     * 产品id
     */
    @TableField("p_id")
    private String pId;
    /**
     * 一级页面pv
     */
    @TableField("first_view_num")
    private Integer firstViewNum;
    /**
     * 二级页面pv
     */
    @TableField("second_view_num")
    private Integer secondViewNum;
    /**
     * 一级页面uv
     */
    @TableField("first_user_num")
    private Integer firstUserNum;
    /**
     * 二级页面uv
     */
    @TableField("second_user_num")
    private Integer secondUserNum;
    /**
     * 设备id
     */
    @TableField("device_flag")
    private String deviceFlag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 浏览类型
     */
    @TableField("type")
    private String type;

    @TableField("userRecord_id")
    private Long userRecordId;

    /**
     * 客户端类型
     */
    @TableField("client_type")
    private String clientType;
    @TableField("user_id")
    private String userId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
