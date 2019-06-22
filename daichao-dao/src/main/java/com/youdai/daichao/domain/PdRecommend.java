package com.youdai.daichao.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 产品推荐 实体类
 * @author Administrator
 *
 */
@Getter
@Setter
@ToString
@Data
public class PdRecommend {

    private int rId;

    private int pId;

    private String pName;

    private int pdStatus;
    
    private int rType;

    private int rSort;
}
