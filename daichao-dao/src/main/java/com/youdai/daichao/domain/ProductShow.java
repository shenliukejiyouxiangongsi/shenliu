package com.youdai.daichao.domain;


import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 产品展示类
 * @author 47302
 *
 */

@Setter
@Getter
@ToString
@Data
public class ProductShow {


    private int pId;

    private String pName;
    
    private String describation;

    private int status;

    private String logoUrl;

    private String linkUrl;

    private String createTime;

    private String updateTime;

    private String rType;
    
    private int	 rSort;

    private int orderNum;
    
    private String clearForm;

    private String price;

    private Integer showNum;

    private Integer sort;

    private String langName;

    private String hasTags;
}
