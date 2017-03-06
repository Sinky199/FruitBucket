/**
 * 深圳佳创视讯技术有限责任公司, 版权所有 违者必究
 * Copyright 2012～2017
 * 13-2-19
 */
package org.test.common.util;

import java.security.MessageDigest;

/**
 * 功能说明：
 *
 * @author li.shiming
 * @Time: 13-2-19 下午6:47
 * @Version: 1.0
 */
public class MD5Util {
    /**
     * MD5加密方法，对传入的参数进行MD5加密
     *
     * @param value
     * @return
     */
    public static String MD5(String value) {
        return byte2hex(encryptMD5(value.getBytes())).toUpperCase();
    }

    private static byte[] encryptMD5(byte[] data) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return md5.digest(data);
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        if (b == null) {
            return hs;
        }
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    public static void main(String[] args) {

    }
}
