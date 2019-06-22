package com.youdai.daichao.resultcommon;

/**
 * @author kouxuhui
 * <p>
 * 2018年1月5日
 */
public class WXUnifyPayReq {

    /**
     * 公众账号ID
     */
    String appid;

	/**
	 * 子商户公众账号ID
	 */
	String sub_appid;

    /**
     * 商户号
     */
    String mch_id;

    /**
     * 子商户号
     */
    String sub_mch_id;

    /**
     * 随机字符串
     */
    String nonce_str;

    /**
     * 签名
     */
    String sign;

    /**
     * 商品描述
     */
    String body;

    /**
     * 商户订单号
     */
    String out_trade_no;

    /**
     * 总金额单位分
     */
    String total_fee;

    /**
     * 终端IP
     */
    String spbill_create_ip;

    /**
     * 交易结束时间
     */
    String time_expire;

    /**
     * 通知地址
     */
    String notify_url;

    /**
     * 交易类型
     */
    String trade_type;

    /**
     * 用户openid
     */
    String openid;
	/**
	 * 子用户用户openid
	 */
	String sub_openid;


	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }


}
