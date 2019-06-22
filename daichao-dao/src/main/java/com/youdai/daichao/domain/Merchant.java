package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.youdai.daichao.util.DateJsonDeserializer;
import com.youdai.daichao.util.DateJsonSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-05-18
 */
@Data
@Accessors(chain = true)
public class Merchant extends Model<Merchant> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type= IdType.AUTO)
    private Long id;
    /**
     * 商品名称
     */
    @TableField("merc_name")
    private String mercName;
    /**
     * 商品内容
     */
    @TableField("merc_content")
    private String mercContent;
    /**
     * 商品原价
     */
    @TableField("merc_price")
    private String mercPrice;
    /**
     * 商品折扣价
     */
    @TableField("discount_price")
    private String discountPrice;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonSerialize(using= DateJsonSerializer.class)
    @JsonDeserialize(using= DateJsonDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
