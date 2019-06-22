package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class Recommend extends Model<Recommend> {

    private static final long serialVersionUID = 1L;

    @TableId("r_id")
    private int rId;
    @TableField("p_id")
    private int pId;
    @TableField("r_type")
    private int rType;
    @TableField("r_sort")
    private int rSort;


    @Override
    protected Serializable pkVal() {
        return this.rId;
    }

}
