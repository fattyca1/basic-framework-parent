package com.laidian.erp.base;

/**
 * <br>系统错误码</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 18:46
 * @since 1.0
 */
public interface BaseErrorCode<T> {

    /**
     * 获取code
     */
    T getCode();

    /**
     * 设置错误码前缀
     *
     * @param baseErrorCodePrefix 错误码前缀
     */
    default T setPrefix(BaseErrorCodePrefix<T> baseErrorCodePrefix) {
        return baseErrorCodePrefix.getPrefix();
    }
}
