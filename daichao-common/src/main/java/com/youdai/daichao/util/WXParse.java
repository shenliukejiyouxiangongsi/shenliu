package com.youdai.daichao.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * @author 占奎
 * @Description
 */
public class WXParse {




    /**
     * 微信请求签名  md5
     *
     * @return
     * @throws Exception
     */
    public static String getReqSign(Map<String, Object> map,String key) throws Exception {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>(map);
        StringBuffer sb = new StringBuffer();
		//所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        String sign = EncryptionUntil.getMD5(sb.toString().getBytes());
        return sign;
    }

	/**
	 * 将json转化为实体POJO
	 * @param jsonStr
	 * @param obj
	 * @return
	 */
	public static<T> Object JSONToObj(String jsonStr,Class<T> obj) {
		T t = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr,
				obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

    /**
     * 验证回调签名
     * @param map
     * @return
     */
    public static boolean isTenpaySign(Map<String, String> map) throws Exception {
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<>();
        for (String parameter : map.keySet()) {
            String parameterValue = map.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        StringBuilder sb = new StringBuilder();
        Set es = packageParams.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append("test");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        //算出签名
        String tobesign = sb.toString();
        String resultSign = EncryptionUntil.getMD5(tobesign.getBytes());
        String tenpaySign = packageParams.get("sign");
        return tenpaySign.equals(resultSign);
    }
}
