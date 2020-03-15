package com.laidian.erp.util.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * <br>AES加密工具</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 22:35
 * @since 1.0
 */
public class AESUtils {

    private static final String KEY_ALGORITHM = "AES";

    /**
     * 默认的加密算法,AES主要有密钥长度，工作模式，填充方式，密钥偏移量 密钥长度默认为128位 工作模式有ECB（电码本模式），CBC（加密块链模式），OFB（输出反馈模式），CFB（加密反馈模式）等
     * 填充方式有NoPadding、PKCS5Padding 密钥偏移量ECB模式不需要
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private static final Integer KEY_LENGTH = 128;

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes(UTF8);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            //通过Base64转码返回
            return Base64.encodeBase64String(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * AES 解密操作
     *
     * @param content 需要解密的内容
     * @param password 密钥
     * @return 解密之后的内容
     */
    public static String decrypt(String content, String password) {
        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, UTF8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 生成加密秘钥
     */
    private static SecretKeySpec getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            kg.init(KEY_LENGTH, new SecureRandom(password.getBytes()));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
