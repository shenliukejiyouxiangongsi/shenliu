package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 匹配推荐表
 * </p>
 *
 * @author zhankui
 * @since 2019-05-15
 */
@Data
@Getter
@Setter
@Accessors(chain = true)
@TableName("match_recommend")
public class MatchRecommend extends Model<MatchRecommend> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mr_id", type = IdType.AUTO)
    private Integer mrId;
    @TableField("p_id")
    private Integer pId;
    /**
     * 0:匹配推荐;
     */
    @TableField("mr_type")
    private Integer mrType;
    /**
     * 排序
     */
    @TableField("mr_sort")
    private Integer mrSort;





    @Override
    protected Serializable pkVal() {
        return this.mrId;
    }

}
