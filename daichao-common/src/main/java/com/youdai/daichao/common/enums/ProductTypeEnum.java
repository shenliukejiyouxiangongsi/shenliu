package com.youdai.daichao.common.enums;

import lombok.Getter;

@Getter
public enum ProductTypeEnum {
    HOT("1","hot"),
    NEW("2","new"),
    BANNER("3","banner");
    ;

    private String code;
    private String type;

    ProductTypeEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }
}
