package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.youdai.daichao.util.DateJsonDeserializer;
import com.youdai.daichao.util.DateJsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-04-01
 */
@Data
@Accessors(chain = true)
@TableName("market_channel")
public class MarketChannel extends Model<MarketChannel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "market_id", type = IdType.AUTO)
    private Integer marketId;
    /**
     * 简称
     */
    @TableField("market_code")
    private String marketCode;
    /**
     * 渠道名称
     */
    @TableField("market_name")
    private String marketName;
    /**
     * 创建时间
     */
    @JsonSerialize(using= DateJsonSerializer.class)
    @JsonDeserialize(using= DateJsonDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @TableField("create_time")
    private Date createTime;


    @TableField("market_type")
    private  Integer marketType;

    @Override
    protected Serializable pkVal() {
        return this.marketId;
    }

}
