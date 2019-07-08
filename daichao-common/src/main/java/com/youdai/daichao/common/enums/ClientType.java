package com.youdai.daichao.common.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ClientType {
    IOS("0","ios"),
    ANDROID("1","android"),
    WEB("2","web");

    ClientType(String code, String type) {
        this.code = code;
        this.type = type;
    }

    private String code;
    private String type;
}
