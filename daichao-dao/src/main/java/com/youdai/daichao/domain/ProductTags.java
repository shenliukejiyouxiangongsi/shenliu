package com.youdai.daichao.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
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
 * 标签表
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
@Data
@Accessors(chain = true)
@TableName("product_tags")
public class ProductTags extends Model<ProductTags> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tag_id",type = IdType.AUTO)
    private Integer tagId;
    @TableField("tag_name")
    private String tagName;
    @TableField("create_time")
    @JsonSerialize(using= DateJsonSerializer.class)
    @JsonDeserialize(using= DateJsonDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.tagId;
    }

}
