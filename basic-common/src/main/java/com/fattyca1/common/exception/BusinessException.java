package com.fattyca1.common.exception;

import com.fattyca1.common.base.BaseEnum;

/**
 * <br>业务异常</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 13:36
 * @since 1.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 2943412316873574040L;

    private Object code;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Object code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Object code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(BaseEnum baseEnum) {
        super(baseEnum.getDesc());
        this.code = baseEnum.getCode();
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
