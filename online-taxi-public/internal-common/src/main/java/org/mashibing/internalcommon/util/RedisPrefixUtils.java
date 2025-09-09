package org.mashibing.internalcommon.util;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 18:30
 */
public class RedisPrefixUtils {


    public  static  String verificationCodePrefix = "passenger-verification-code-";

    //token存储的前缀
    public static String TokenPrefix = "token-";

    /**
     * 根据手机生成 key
     * @param passengerPhone
     * @return
     */
    public static  String passengerKeyByPhone(String passengerPhone){
        return  verificationCodePrefix+passengerPhone;
    }

    /**
     * 根据手机号和标识生成token
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String phone,String identity) {
        return TokenPrefix + phone +"-"+identity;
    }

}
