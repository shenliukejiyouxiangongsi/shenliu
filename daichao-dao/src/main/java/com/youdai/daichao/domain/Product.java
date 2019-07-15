package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@Data
@ToString
@TableName("product")
@Accessors(chain = true)
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "p_id", type = IdType.AUTO)
    private Integer pId;
    @TableField("p_name")
    private String pName;
    private String describation;
    @TableField("min_money")
    private String minMoney;
    @TableField("max_money")
    private String maxMoney;
    @TableField("outTime_begin")
    private String outtimeBegin;
    @TableField("outTime_end")
    private String outtimeEnd;
    private String rate;
    @TableField("access_condition")
    private String accessCondition;
    private String materials;
    private String explaintion;
    private Integer status;
    @TableField("logo_url")
    private String logoUrl;
    @TableField("link_url")
    private String linkUrl;
    @TableField("merchant_id")
    private String merchantId;
    @TableField("create_time")
    private String createTime;
    @TableField("update_time")
    private String updateTime;
    @TableField("create_user")
    private String createUser;
    @TableField("p_type")
    private String pType;
    @TableField("order_num")
    private Integer orderNum;
    @TableField("clear_form")
    private String clearForm;
    @TableField("price")
    private BigDecimal price;
    @TableField("show_num")
    private Integer showNum;
    @TableField("sort")
    private Integer sort;
    @TableField("has_tags")
    private String hasTags;

    @TableField(exist = false)
    private int lastInt;
    @TableField(exist = false)
    private int nextInt;

    @Override
    protected Serializable pkVal() {
        return this.pId;
    }

}
