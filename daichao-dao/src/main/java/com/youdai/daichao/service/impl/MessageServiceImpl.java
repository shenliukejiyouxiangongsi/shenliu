package com.youdai.daichao.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.domain.MsgModel;
import com.youdai.daichao.domain.MsgSendInfo;
import com.youdai.daichao.mapper.MsgModelMapper;
import com.youdai.daichao.mapper.MsgSendInfoMapper;
import com.youdai.daichao.service.MessageService;
import com.youdai.daichao.service.MessageXsendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {



    @Autowired
    private MessageXsendService messageXsendService;

    @Autowired
    private MsgModelMapper msgModelMapper;

    @Autowired
    private MsgSendInfoMapper msgSendInfoMapper;

    public void send(String phone, Map<String, String> para, String tpId) {

        String msgContent;
        String errorContent;


        //获取短信模板
        EntityWrapper<MsgModel> msgModelEntityWrapper = new EntityWrapper<>();
        //msgModelEntityWrapper.eq("tp_id","RR1jD4");
        msgModelEntityWrapper.eq("tp_id", tpId);
        msgModelEntityWrapper.eq("status", 1);


        List<MsgModel> msgModels = msgModelMapper.selectList(msgModelEntityWrapper);


        //保持数据到表
        MsgSendInfo msgSendInfo = new MsgSendInfo();
        msgSendInfo.setSendPhone(phone);
        msgSendInfo.setTpId(tpId);
        msgSendInfo.setCreateDate(new Date());


        if (msgModels.size() != 1) {
            errorContent = "获取模板失败，实际获取到模板数：" + msgModels.size();
            //log.error(errorContent);
            msgSendInfo.setResultContent(errorContent);
            msgSendInfo.setStatus(1);
            msgSendInfoMapper.insert(msgSendInfo);
            return;
        }

        MsgModel msgModel = msgModels.get(0);

        msgModel.getContent();

        //处理短信拼装
        String content = msgModel.getContent().replaceAll("</?[^>]+>", "");
        for (Map.Entry<String, String> entry : para.entrySet()) {
            content = content.replace("#" + entry.getKey(), entry.getValue());
        }
        msgContent = content;
        para.put("msg",content);
        //调用赛邮短信发送接口

        msgSendInfo.setSendRoad("sms_chuanglan");

//        Map<String, String> resultMap = messageXsendService.smsXsend(phone, para, tpId);

        Map<String, String> resultMap = messageXsendService.smsXsendCL(phone, para);
        //如果调用成功
        if ("success".equals(resultMap.get("status"))) {
            msgSendInfo.setResultContent(resultMap.toString());
            msgSendInfo.setStatus(1);
        } else {
            msgSendInfo.setResultContent(resultMap.toString());
            msgSendInfo.setStatus(2);
        }
        //否则调用鼎汉通道

        msgSendInfo.setSendContent(msgContent);
        msgSendInfoMapper.insert(msgSendInfo);


    }
}
