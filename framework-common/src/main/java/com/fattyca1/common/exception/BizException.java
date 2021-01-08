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
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 2943412316873574040L;

    private Object code;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Object code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Object code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(BaseEnum baseEnum) {
        super(baseEnum.getDesc());
        this.code = baseEnum.getCode();
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
