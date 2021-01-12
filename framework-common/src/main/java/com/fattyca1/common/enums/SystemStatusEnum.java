package com.fattyca1.common.enums;

import com.fattyca1.common.base.BaseEnum;

/**
 * <br>系统异常枚举</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 13:10
 * @since 1.0
 */
public enum SystemStatusEnum implements BaseEnum<Integer> {
    /**
     * 接口熔断
     */
    HYSTRIX_RETURN_CODE(602, "接口熔断"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(600, "业务异常"),

    /**
     * 服务器异常
     */
    DEFAULT_ERR_CODE(500, "服务器异常"),
    /**
     * 接口超时
     **/
    INTERFACE_TIMEOUT(504, "接口超时"),
    /**
     * 无效参数
     */
    INVALID_PARAM(400, "无效参数"),
    /**
     * 资源不存在
     **/
    RECORD_NOT_EXISTS(404, "资源不存在"),
    /**
     * 不支持的请求方式
     */
    METHOD_NOT_ALLOWED(405, "不支持的请求方式"),
    /**
     * 拒绝访问
     **/
    FORBIDDEN(403, "拒绝访问"),
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功");


    public final Integer code;
    public final String desc;

    SystemStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
