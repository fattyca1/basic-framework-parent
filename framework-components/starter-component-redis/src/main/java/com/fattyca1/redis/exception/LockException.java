package com.fattyca1.redis.exception;

/**
 * <br>Redis异常</br>
 *
 * @author fattyca1
 */
public class LockException extends RuntimeException {


    public LockException() {
    }

    public LockException(String msg) {
        super(msg);
    }

    public LockException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
