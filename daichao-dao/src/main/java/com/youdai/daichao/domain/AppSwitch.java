package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-03-20
 */
@Data
@Accessors(chain = true)
@TableName("app_switch")
public class AppSwitch extends Model<AppSwitch> {

    private static final long serialVersionUID = 1L;
    /**
     * @备注:主键id
     * @字段:id BIGINT(19)
     */
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("status")
    private Integer status;
    @TableField("app_type")
    private Integer appType;
    private String remark;
    @TableField("app_version")
    private String appVersion;


    @JsonSerialize(using= DateJsonSerializer.class)
    @JsonDeserialize(using= DateJsonDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @TableField("create_time")
    private Date createTime;

    @TableField("package_name")
    private String packageName;


    @TableField("market_id")
    private Integer marketId;

    @TableField("shell_id")
    private Integer shellId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
