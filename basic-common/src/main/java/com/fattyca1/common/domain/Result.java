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

    /**
     * 表示接口调用成功
     */
    public static final int SUCCESS_CODE = 1;
    /**
     * 表示接口调用失败
     */
    public static final int FAIL_CODE = -1;

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

    @Override
    public String toString() {
        return "Result [code=" + code + ", message=" + message + ", result=" + result + ", data=" + data + "]";
    }
}