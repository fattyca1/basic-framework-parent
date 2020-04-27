package com.fattyca1.common.exception;

/**
 * <br>Redis异常</br>
 *
 * @author fattyca1
 */
public class UnhandledRedisException extends RuntimeException {


    public UnhandledRedisException() {
    }

    public UnhandledRedisException(String msg) {
        super(msg);
    }

    public UnhandledRedisException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
