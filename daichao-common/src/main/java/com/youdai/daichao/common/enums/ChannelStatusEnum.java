package com.youdai.daichao.common.enums;

import lombok.Getter;

@Getter
public enum ChannelStatusEnum {
    ONLINE("1","上线"),
    UNDERLINE("0","下线")
    ;

    private String status;
    private String msg;

    ChannelStatusEnum(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
