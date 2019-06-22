package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zhankui
 * @since 2019-05-15
 */
@Data
@Accessors(chain = true)
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 上游订单号
     */
    @TableField("trade_no")
    private String tradeNo;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 渠道,1:支付宝；2:微信
     */
    private Integer channel;
    /**
     * 金额
     */
    private String mount;
    /**
     * 状态,1:成功；2.失败；3.支付中 4.交易超时
     */
    private Integer status;
    /**
     * 结果信息
     */
    @TableField("result_msg")
    private String resultMsg;
    /**
     * 报告状态，1.成功；2.失败
     */
    @TableField("report_status")
    private Integer reportStatus;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 下单时间
     */
    @TableField("order_time")
    private String orderTime;
    /**
     * 付款时间
     */
    @TableField("pay_time")
    private String payTime;
    /**
     * 手机号
     */
    @TableField("phone_no")
    private String phoneNo;
    /**
     * 身份证号
     */
    @TableField("idcard_no")
    private String idcardNo;
    /**
     * 身份证姓名
     */
    @TableField("idcard_name")
    private String idcardName;
    /**
     * 手机系统类型
     */
    @TableField("phone_type")
    private String phoneType;
    /**
     * 报告编号
     */
    @TableField("report_number")
    private String reportNumber;
    @TableField(exist = false)
    private Long mercId;
    @TableField(exist = false)
    private String mercName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
