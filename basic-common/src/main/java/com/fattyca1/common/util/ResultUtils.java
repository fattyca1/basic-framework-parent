package com.fattyca1.common.util;

import com.fattyca1.common.domain.Result;
import com.fattyca1.common.exception.BusinessException;
import com.fattyca1.common.exception.SystemException;
import java.util.Objects;

/**
 * <br>返回结果工具类</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 23:08
 * @since 1.0
 */
public class ResultUtils {

    /**
     * 结果是否有效
     *
     * @param result 获取返回结果且返回结果不为空为true
     */
    public static boolean isValid(Result<?> result) {
        return Objects.nonNull(result) && Result.RESULT_SUCCESS.equals(result.getResult()) && Objects
            .nonNull(result.getData());
    }

    /**
     * 调用是否成功
     */
    public static <T> boolean isSuccess(Result<T> result) {
        return Objects.nonNull(result) && Result.RESULT_SUCCESS.equals(result.getResult());
    }

    /**
     * 获取返回结果，失败结果为null
     */
    public static <T> T getResultWithNoException(Result<T> source) {
        if (Objects.nonNull(source) && Result.RESULT_SUCCESS.equals(source.getResult())) {
            return source.getData();
        }
        return null;
    }

    /**
     * 获取返回结果，调用失败抛出异常
     */
    public static <T> T getResultWithException(Result<T> source) {
        if (Objects.nonNull(source) && Result.RESULT_SUCCESS.equals(source.getResult())) {
            return source.getData();
        }
        if (Result.RESULT_FAIL.equals(source.getResult())) {
            throw new BusinessException(source.getCode(), source.getMessage());
        }
        throw new SystemException(source.getCode(), source.getMessage());
    }
}
