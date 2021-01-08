package com.fattyca1.common.base;

/**
 * <br>枚举基础类</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 11:58
 * @since 1.0
 */
public interface BaseEnum<T> {

    /**
     * 获取code
     *
     * @return 返回枚举code
     */
    T getCode();

    /**
     * 获取描述
     *
     * @return 返回枚举desc
     */
    String getDesc();
}
