package com.youdai.daichao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by chengwenwen on 2016/12/20.
 */
public class OrderUtils {
    private static long orderNum = 0l;
    private static String date;

    public static synchronized String getSizeNumber(int size) {
        Random r = new Random();
        String str="";
        for(int i=0;i<size;i++){
            str+=r.nextInt(10);
        }
        return str;
    }

    /**
     * 生成订单编号
     *
     * @return
     */
    public static synchronized String getOrderNo() {
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if (date == null || !date.equals(str)) {
            date = str;
            orderNum = 0l;
        }
        orderNum++;
        long orderNo = Long.parseLong((date)) * 10000;
        orderNo += orderNum;
        return orderNo + "";
    }

    /**
     * 余额支付交易订单编号
     *
     * @return
     */
    public static synchronized String getBalancePayNo() {
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if (date == null || !date.equals(str)) {
            date = str;
            orderNum = 0l;
        }
        orderNum++;
        long orderNo = Long.parseLong((date)) * 10000;
        orderNo += orderNum;
        return "xx4009772001" + orderNo + "";
    }



    public static    String generalOrderId(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replaceAll("-","").substring(0,15);
        String order_id = DateUtils2.format("yyyyMMddHHmmssSSS",new Date()) +str;
        return  order_id;
    }
}
