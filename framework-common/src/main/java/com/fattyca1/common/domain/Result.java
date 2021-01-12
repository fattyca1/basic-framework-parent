package com.fattyca1.common.domain;

import com.fattyca1.common.base.BaseEnum;
import com.fattyca1.common.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

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

    private String requestId;
    /**
     * 状态码
     */
    private Object code;

    /***
     * 说明信息
     */
    private String message;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回数据
     */
    private T data;


    public Result(){
        this(null, null, "", SUCCESS, null);
    }

    public Result(T data){
        this(null, null, "", SUCCESS, data);
    }

    public Result(Object code, String msg){
        this(null, code, msg, FAILURE, null);
    }

    public Result(BaseEnum<?> baseEnum) {
        this(baseEnum.getCode(), baseEnum.getDesc());
    }

    /**
     * 成功
     */
    public static final boolean SUCCESS = true;
    /**
     * 失败
     */
    public static final boolean FAILURE = false;


    public static Result<Void> success(){
        return new Result<>();
    }

    public static <R> Result<R> success(R data){
        return new Result<R>(data);
    }

    public static Result<Void> fail(Object code, String msg){
        return new Result<>(code, msg);
    }

    public static Result<Void> fail(){
        return new Result<>(null, null);
    }

    public static <R> Result<R> with(Object code, String msg, boolean success, R data) {
        return new Result<R>(null, code, msg, success, data);
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

}