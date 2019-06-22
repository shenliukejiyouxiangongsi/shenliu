package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-03-12
 */
@Data
@Accessors(chain = true)
public class Project extends Model<Project> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pro_id", type = IdType.AUTO)
    private int proId;
    /**
     * 项目名称
     */
    @TableField("pro_name")
    private String proName;
    /**
     * 唯一标识
     */
    @TableField("pro_key")
    private String proKey;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

    private String description;

    @Override
    protected Serializable pkVal() {
        return this.proId;
    }

}
