package com.laidian.erp.aop;

import com.laidian.erp.domain.Result;
import com.laidian.erp.enums.SystemStatusEnum;
import com.laidian.erp.exception.BusinessException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br>全局异常处理</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 17:44
 * @since 1.0
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 业务异常统一捕获
     *
     * @param req 请求
     * @param e 业务异常
     * @return 对应结果
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result<String> businessExceptionHandler(HttpServletRequest req, BusinessException e) {
        // 已知异常不打印堆栈，避免多余的日志IO输出
        return new Result<>(e.getCode(), e.getMessage());
    }

    /**
     * 未知异常统一捕获
     * @param req 请求
     * @param e 未知异常
     * @return 对应结果
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        // 错误的请求方式(需要GET的使用了POST)
        if(e instanceof HttpRequestMethodNotSupportedException){
            return new Result<>(SystemStatusEnum.METHOD_NOT_ALLOWED);
        }
        // 输入参数类型不正确 || 输入参数不全
        if(e instanceof TypeMismatchException
            || e instanceof MissingServletRequestParameterException){
            return new Result<>(SystemStatusEnum.INVALID_PARAM);
        }
        // 输入参数不满足约束(spring mvc框架的注解校验)
        if(e instanceof ValidationException || e instanceof MethodArgumentNotValidException){
            return new Result<>(SystemStatusEnum.INVALID_PARAM);
        }
        // 后续如果有其他明确的异常，继续细化补充
        log.error("【Exception】异常, 请求方法:{}, 请求参数:{}", req.getRequestURI(), req.getQueryString(), e);
        // todo 未知的异常，应该格外注意，可以发送邮件通知等，只是打印堆栈，是没有人会去看日志的
        return new Result<>(SystemStatusEnum.DEFAULT_ERR_CODE);
    }
}
