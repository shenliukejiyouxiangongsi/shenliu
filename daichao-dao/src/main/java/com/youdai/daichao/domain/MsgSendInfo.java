package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @version : Ver 1.0
 * @MsgSendInfo
 * @(msg_send_info)
 */
@TableName("msg_send_info")
public class MsgSendInfo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * @备注:
     * @字段:id BIGINT(19)
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * @备注:模板编号
     * @字段:tp_id VARCHAR(20)
     */
    @TableField("tp_id")
    private String tpId;


    /**
     * @备注:使用通道
     * @字段:send_road VARCHAR(50)
     */
    @TableField("send_road")
    private String sendRoad;


    /**
     * @备注:发送电话号码
     * @字段:send_phone VARCHAR(30)
     */
    @TableField("send_phone")
    private String sendPhone;


    /**
     * @备注:短信内容
     * @字段:send_content TEXT(65535)
     */
    @TableField("send_content")
    private String sendContent;


    /**
     * @备注:发送状态：0：未发送;1:推送短信平台成功2：推送短信平台失败
     * @字段:status INT(10)
     */
    private Integer status;


    /**
     * @备注:短信平台返回内容
     * @字段:result_content TEXT(65535)
     */
    @TableField("result_content")
    private String resultContent;


    /**
     * @备注:创建人id
     * @字段:create_by VARCHAR(50)
     */
    @TableField("create_by")
    private String createBy;


    /**
     * @备注:创建时间
     * @字段:create_date TIMESTAMP(19)
     */
    @TableField("create_date")
    private java.util.Date createDate;


    /**
     * @备注:修改人
     * @字段:update_by VARCHAR(50)
     */
    @TableField("update_by")
    private String updateBy;


    /**
     * @备注:修改日期
     * @字段:update_date TIMESTAMP(19)
     */
    @TableField("update_date")
    private java.util.Date updateDate;


    public MsgSendInfo() {
    }

    public MsgSendInfo(
            Long id
    ) {
        this.id = id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpId() {
        return this.tpId;
    }


    public void setSendRoad(String sendRoad) {
        this.sendRoad = sendRoad;
    }

    public String getSendRoad() {
        return this.sendRoad;
    }


    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getSendPhone() {
        return this.sendPhone;
    }


    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendContent() {
        return this.sendContent;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }


    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }

    public String getResultContent() {
        return this.resultContent;
    }


    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }


    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.util.Date getCreateDate() {
        return this.createDate;
    }


    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }


    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }
}
