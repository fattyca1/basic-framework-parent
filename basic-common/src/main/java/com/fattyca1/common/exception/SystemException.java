package com.fattyca1.common.exception;

import com.fattyca1.common.base.BaseEnum;

/**
 * <br>系统异常</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 13:36
 * @since 1.0
 */
public class SystemException extends RuntimeException {

    private Object code;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Object code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Object code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public SystemException(BaseEnum baseEnum) {
        super(baseEnum.getDesc());
        this.code = baseEnum.getCode();
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public Object getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
