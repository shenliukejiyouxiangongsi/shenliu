package com.youdai.daichao.common.vo;

import lombok.Data;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/14 0014
 * @Description
 */
@Data
public class ProductVo {

    private int pId;
    private String pName;
    private String describation;
    private String minMoney;
    private String maxMoney;
    private String outtimeBegin;
    private String outtimeEnd;
    private String rate;
    private String logoUrl;
    private String linkUrl;
    private int status;
    private int sort;
    private int orderNum;
    private String hasTags;
    private String type;
}
