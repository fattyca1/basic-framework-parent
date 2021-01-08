package com.fattyca1.common.util.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * <br>MD5加密</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/21 11:51
 * @since 1.0
 */
public class MD5Utils {

    private static final String MD5 = "MD5";

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    /**
     * md5加密
     * @param data 需要加密的字符串
     * @return 加密之后的16进制字符串
     */
    public static String md5LowerHexStr(String data) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md5 = MessageDigest.getInstance(MD5);
            // 加密后的字符串
            return HexUtils.bytestoLowerHexStr(md5.digest(data.getBytes(UTF8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
