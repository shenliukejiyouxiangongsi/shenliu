package com.youdai.daichao.service;

import java.util.Map;

public interface MessageService {
    void send(String phone, Map<String, String> para, String tpId);
}
