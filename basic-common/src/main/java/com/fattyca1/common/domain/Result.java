package com.fattyca1.common.domain;

import com.fattyca1.common.base.BaseEnum;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <br>返回对象</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 11:34
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -2804195259517755050L;

    /**
     * 表示接口调用成功
     */
    public static final int SUCCESS_CODE = 1;
    /**
     * 表示接口调用失败
     */
    public static final int FAIL_CODE = -1;
    /**
     * 表示没有权限调用该接口
     */
    public static final int NO_PERMISSION = -2;
    /**
     * 表示未登录或者登录过期
     */
    public static final int NO_LOGIN = -3;
    /**
     * 含义:表示token错误导致解析失败<br>
     */
    public static final int TOKEN_ERROR = -4;

    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_FAIL = "fail";

    /**
     * 状态码
     */
    private Object code;

    /***
     * 说明信息
     */
    private String message;

    /**
     * 返回结果 fail、success
     */
    private String result;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
        this(SUCCESS_CODE, "成功", RESULT_SUCCESS, null);
    }

    public Result(Object code, String msg) {
        this(code, msg, RESULT_FAIL, null);
    }

    public Result(BaseEnum baseEnum) {
        this(baseEnum.getCode(), baseEnum.getDesc(), RESULT_FAIL, null);
    }

    public Result(T data) {
        this(SUCCESS_CODE, "成功", RESULT_SUCCESS, data);
    }

    public static <T> Result<T> successed() {
        return with(SUCCESS_CODE, "成功", RESULT_SUCCESS, null);
    }

    public static <T> Result<T> successed(T data) {
        return with(SUCCESS_CODE, "成功", RESULT_SUCCESS, data);
    }

    public static <T> Result<T> failed(BaseEnum baseEnum) {
        return with(baseEnum.getCode(), baseEnum.getDesc(), RESULT_FAIL, null);
    }

    public static <T> Result<T> failed(Object code, String msg) {
        return with(code, msg, RESULT_FAIL, null);
    }

    public static <T> Result<T> with(Object code, String message, String result, T data) {
        return new Result<>(code, message, result, data);
    }


    @Override
    public String toString() {
        return "Result [code=" + code + ", message=" + message + ", result=" + result + ", data=" + data + "]";
    }
}