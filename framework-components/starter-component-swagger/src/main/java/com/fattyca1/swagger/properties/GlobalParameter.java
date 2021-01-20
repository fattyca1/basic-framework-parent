package com.fattyca1.swagger.properties;

import lombok.Data;

/**
 * <br>全局参数</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@Data
public class GlobalParameter {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 参数类型
     */
    private String modelRef;

    /**
     * 是否是必传字段
     */
    private boolean require;

    /**
     * 请求参数类型
     */
    private String parameterType;

    /**
     * 默认值
     */
    private String defaultValue;
}
