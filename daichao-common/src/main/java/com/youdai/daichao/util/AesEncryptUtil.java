package com.youdai.daichao.util;

//import org.apache.shiro.codec.Base64;
import cn.hutool.core.codec.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密
 */
public class AesEncryptUtil {

    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = EncryptedString.key;

    private static String IV = EncryptedString.iv;

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"NoPadding PkcsPadding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }



    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {

        String test1 = "{\n" +
                "    \t\"code\":\"SUCCESS\",\n" +
                "    \t\"data\":{\n" +
                "    \t\t\"pageNum\":1,\n" +
                "    \t\t\"pageSize\":10,\n" +
                "    \t\t\"total\":2,\n" +
                "    \t\t\"pages\":1,\n" +
                "    \t\t\"list\":[\n" +
                "    \t\t\t{\n" +
                "    \t\t\t\t\"describation\":\"秒下款，必提额\",\n" +
                "    \t\t\t\t\"minMoney\":\"2800\",\n" +
                "    \t\t\t\t\"maxMoney\":\"1万\",\n" +
                "    \t\t\t\t\"outtimeBegin\":\"7\",\n" +
                "    \t\t\t\t\"outtimeEnd\":\"30\",\n" +
                "    \t\t\t\t\"rate\":\"0.02\",\n" +
                "    \t\t\t\t\"logoUrl\":\"https://shenliu-daichao.oss-cn-hangzhou.aliyuncs.com/prod/daichao/product/1562234704830.png\",\n" +
                "    \t\t\t\t\"linkUrl\":\"http://t.cn/Ai0kj6sU\",\n" +
                "    \t\t\t\t\"status\":1,\n" +
                "    \t\t\t\t\"sort\":9,\n" +
                "    \t\t\t\t\"orderNum\":821,\n" +
                "    \t\t\t\t\"hasTags\":\"期限长,\n" +
                "    \t\t\t\t急速放款\",\n" +
                "    \t\t\t\t\"pname\":\"金币辉煌\",\n" +
                "    \t\t\t\t\"pid\":79\n" +
                "    \t\t\t},\n" +
                "    \t\t\t{\n" +
                "    \t\t\t\t\"describation\":\"秒下款，必提额\",\n" +
                "    \t\t\t\t\"minMoney\":\"2000\",\n" +
                "    \t\t\t\t\"maxMoney\":\"5000\",\n" +
                "    \t\t\t\t\"outtimeBegin\":\"7\",\n" +
                "    \t\t\t\t\"outtimeEnd\":\"45\",\n" +
                "    \t\t\t\t\"rate\":\"0.02\",\n" +
                "    \t\t\t\t\"logoUrl\":\"https://shenliu-daichao.oss-cn-hangzhou.aliyuncs.com/prod/daichao/product/1562295619705.png\",\n" +
                "    \t\t\t\t\"linkUrl\":\"http://anxh.qxykjz.com/merchantProduct20190520182102/merchantProduct20190520182102_1e3f32d1167d4d1fb35b/merchantProduct20190520182102_1e3f32d1167d4d1fb35b_1.html\",\n" +
                "    \t\t\t\t\"status\":1,\n" +
                "    \t\t\t\t\"sort\":13,\n" +
                "    \t\t\t\t\"orderNum\":194,\n" +
                "    \t\t\t\t\"hasTags\":\"期限长,\n" +
                "    \t\t\t\t要求低,\n" +
                "    \t\t\t\t急速放款\",\n" +
                "    \t\t\t\t\"pname\":\"安薪花\",\n" +
                "    \t\t\t\t\"pid\":82\n" +
                "    \t\t\t}\n" +
                "    \t\t],\n" +
                "    \t\t\"isFirstPage\":true,\n" +
                "    \t\t\"isLastPage\":true,\n" +
                "    \t\t\"hasPreviousPage\":false,\n" +
                "    \t\t\"hasNextPage\":false\n" +
                "    \t},\n" +
                "    \t\"msg\":\"成功\",\n" +
                "    \t\"success\":true\n" +
                "    }";
        String test =new String(test1.getBytes(),"UTF-8");
        String data = null;
        String key =  KEY;
        String iv = IV;
        // /g2wzfqvMOeazgtsUVbq1kmJawROa6mcRAzwG1/GeJ4=
        data = encrypt(test, key, iv);
        System.out.println("数据："+test);
        System.out.println("加密："+data);
        String jiemi =desEncrypt(data, key, iv).trim();
        System.out.println("解密："+jiemi);


    }
}
