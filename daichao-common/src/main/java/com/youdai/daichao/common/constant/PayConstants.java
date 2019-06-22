package com.youdai.daichao.common.constant;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/17
 * @Description
 */
public class PayConstants {


    /***支付宝*/
    //appid
    public static final String ALI_APP_ID="2019051564661396";
    //pkcs8的私钥
    public static final String ALI_APP_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCmweUpl35Xwty2DiDk6WSu3gof2ujysE5+d20yYVMHcnYSZ5P3ieCKgdL//55+IFVQEIqgpZUUlA9+LBRUzYVpkpranXowyVWEvghJcy9B1YbYfMJZ+0zNZ8PkdPurK5qNUBbXVdmu3QewvthE5We0ND8pDIAkX6CCWkrPzUa346mcz+ekyJoj2AyWWfQ9Cj9sq7k57BOpUfP0QUw/I6FzEJCIxbDCqc747yEi0ITQuiNBmTkQOCi1bUExPuhd+DikVrmid3oI/tA8Mw5/NO0K1qJgRr8H58LdiUnLD3LinzCA5iBfF6kE663Qci7gSi31u8Yqn5r2bYYmOSPk+M1lAgMBAAECggEAH0tmAT8v87JbD0NpguwV/AcpaT8A1oKlyHALKxg+p7ggVmXfy+WxIO0qNua2zL6uo/7mtS095AXBPpFxI1UKg3yC2SISi1NNXEL5dSdLqBQ1YS1Qn+hcSnP9vEMD8+ST9Y8hKjovRvSt+8pk9dEDfDsN21tANRNJIQ1m2saQTfP5qWikayTNVrK9cs7cs2yzjMUwGI04y3cmFQbaB2GHIfl1cPOT0jN0FwyySvyUq1L/aaNFF/sZjqSf7NMEH8PmZqQUmecDnF6e5ibAjA5N6F+NxVlJ8Q8YVdtjrGBUTFGTpKg5lcVV7xueTaoLj1IjkJJp6RvAx96fc6zGN0Un4QKBgQD6HazD0CXNpnmi9N9TS8frw8G4iOw+ottZw7ck9Lzhk4GipMNHAWypC4yyM+vvZJwXIZN4lNnSqoa/hnFNvwxuHmVITePurCPUx2BE02KKoY5Gw1SnKmuzxiPGjW6TE4B4Zf4P6r76FNIvRo447NH9nV6mQseKJ/PUumsbum3HTQKBgQCqrjFm0RfEceCgV1n1tvg0yCQ+uYGFdQg7OrLYVevOpCli8anZ7gwU8+y9aUPhazVdhKW36/4pNouI0W+7E1D9HMxN4h/TJQaKtTXkkVk+Xe3U7FTqVhA7CD/JDuDoNGgszuourXFa26s68ooerYR+Ir7cRI9HaU65ja+6XCMCeQKBgQDCrMpF9wMVmFfgsHwK11caeNmVim1nK1dT/PPV/e/uG+Ow9lFQORmcUfMG2Ubwo/yiPG4OUr1gLX6IIFqcSvGf3GS07HtW0AFromuZ5nYytSCKj4qaOnhuCvDZoEfKjklWbPxc4zgWapJWXbJhOGMR5aU//4ZUUFiG4Tpw8KB1mQKBgGMqU/+VyoZTV5wxRE4z4V/hTNW4uB0PExo+j3yL5zanQHiBShsZaKGf21NtXogQ3u+XyyTiE0hkrYtvi8gYf/yfh8BUegMWRwe+IzLKlBxdPHpyWR/Roxp5P34Ui+29zS0DhyJZmTud142nL0KFtC4JB/U4TDrEX34FaS0QrTKZAoGBAPNWrPrbXQ4WGt3zhG92anvI98M1Zq7fBu4oJSD+jjHisbHEd7FrFoh7MENEQSxugJymtwquwJmbmX68EVLgaTjQUROhKrqSAMaOWfv2D7xiMnM/f2aSzRbqFAGLoch95PMBA/KFu6Xw8bU0GSfYosOgybyfpvxaqXpaVS8bBU5F";
    //支付宝公钥
    public static final  String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjxl0Hqz5TGEJlS1VksTbVPpGJf4s7vhgQfK9GERmseFlt6Zs3ZaBjVEpXoHAgZH3tE1wb3mmOJp170WSPqXCNt6L6Pa0CWwn1NVWH4OEl+1tUu4ANVvI0qnUSFvOWYqZlfkR1Jl53XAGtZUHqdNg/HmS6eGmKTgNfw0NsROsoztbVdvBX00pCytuvlZNJXKWsJW/8OCFmAuObEnkbZrOMQkiMAt4ELpuglhKxwLO7lmB+SgunRbI93yokeMMRyLsAAJI1bPykqAJsWOPTR1S2FYIxet5GtmocTLHx4G6XiSosKLtQ27ArYWdCIMpKgvjCyWt/nmPKDdDPg7XUQAZmwIDAQAB";
    //签名方式
    public static final String sign_type="RSA2";
    //编码格式
    public static final String CHARSET="utf-8";
    //正式环境支付宝网关
    public static final String ALI_URL="https://openapi.alipay.com/gateway.do";




    //我方微信APPid秘钥
    public static final String WEIXIN_APIKEY = "mytest111";
    //微信统一下单接口
    public static final String unityPayUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";

}
