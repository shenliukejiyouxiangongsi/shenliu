package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

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
 * app壳
 * </p>
 *
 * @author wangjiabin
 * @since 2019-04-04
 */
@Data
@Accessors(chain = true)
public class Shell extends Model<Shell> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "shell_id", type = IdType.AUTO)
    private Integer shellId;
    /**
     * 包名
     */
    @TableField("shell_name")
    private String shellName;

    /**
     * 创建时间
     */
    @JsonSerialize(using= DateJsonSerializer.class)
    @JsonDeserialize(using= DateJsonDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.shellId;
    }

}
