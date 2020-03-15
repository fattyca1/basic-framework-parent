package com.laidian.erp.util.security;

/**
 * <br>16进制工具</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/21 11:53
 * @since 1.0
 */
public class HexUtils {
    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 字符串转换成小写十六进制字符串
     *
     * @param str str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2LowerHexStr(String str) {
        byte[] bytes = str.getBytes();
        return bytestoLowerHexStr(bytes);
    }

    /**
     * 字符串转换成大写十六进制字符串
     *
     * @param str str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2UpperHexStr(String str) {
        byte[] bytes = str.getBytes();
        return bytestoUpperHexStr(bytes);
    }

    /**
     * 转换为小写的16进制
     * @param bytes 字节码
     * @return 16进制字符串
     */
    public static String bytestoLowerHexStr(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(DIGITS_LOWER[(aByte >> 4) & 0x0f]);
            ret.append(DIGITS_LOWER[aByte & 0x0f]);
        }
        return ret.toString();
    }

    /**
     * 转换为大写的16进制
     * @param bytes 字节码
     * @return 16进制字符串
     */
    public static String bytestoUpperHexStr(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(DIGITS_UPPER[(aByte >> 4) & 0x0f]);
            ret.append(DIGITS_UPPER[aByte & 0x0f]);
        }
        return ret.toString();
    }
}
