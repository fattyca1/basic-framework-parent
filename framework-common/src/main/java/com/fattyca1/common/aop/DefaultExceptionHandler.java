package com.fattyca1.common.aop;

import com.fattyca1.common.domain.Result;
import com.fattyca1.common.enums.SystemStatusEnum;
import com.fattyca1.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <br>全局异常处理</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 17:44
 * @since 1.0
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 业务异常统一捕获
     *
     * @param req 请求
     * @param e 业务异常
     * @return 对应结果
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<Result<?>> businessExceptionHandler(HttpServletRequest req, BizException e) {
        // 已知异常不打印堆栈，避免多余的日志IO输出
        log.warn("【Exception】, request method:{} ", req.getRequestURI(), e);
        String errorCode =e.getCode() + "";
        if (SystemStatusEnum.SESSION_TIMEOUT.getCode().equals(errorCode)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.fail(errorCode, e.getMessage()));
        }
        if (SystemStatusEnum.DEFAULT_ERR_CODE.getCode().equals(errorCode)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.fail(errorCode, e.getMessage()));
        }
        errorCode = StringUtils.isBlank(errorCode) ? SystemStatusEnum.DEFAULT_ERR_CODE.getCode() + "": errorCode;
        return ResponseEntity.ok(Result.fail(errorCode, e.getMessage()));
    }

    /**
     * 未知异常统一捕获
     * @param req 请求
     * @param e 未知异常
     * @return 对应结果
     * @throws Exception
     */
//    @ExceptionHandler(value = Exception.class)
//    public Result<String> exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
//        // 错误的请求方式(需要GET的使用了POST)
//        if(e instanceof HttpRequestMethodNotSupportedException){
//            return new Result<>(SystemStatusEnum.METHOD_NOT_ALLOWED);
//        }
//        // 输入参数类型不正确 || 输入参数不全
//        if(e instanceof TypeMismatchException
//            || e instanceof MissingServletRequestParameterException){
//            return new Result<>(SystemStatusEnum.INVALID_PARAM);
//        }
//        // 输入参数不满足约束(spring mvc框架的注解校验)
//        if(e instanceof ValidationException || e instanceof MethodArgumentNotValidException){
//            return new Result<>(SystemStatusEnum.INVALID_PARAM);
//        }
//        // 后续如果有其他明确的异常，继续细化补充
//        log.error("【Exception】异常, 请求方法:{}, 请求参数:{}", req.getRequestURI(), req.getQueryString(), e);
//        // todo 未知的异常，应该格外注意，可以发送邮件通知等，只是打印堆栈，是没有人会去看日志的
//        return new Result<>(SystemStatusEnum.DEFAULT_ERR_CODE);
//    }
}
