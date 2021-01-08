package com.fattyca1.redis.function;

/**
 * <br>函数接口</br>
 *
 * @author fattyca1
 */
@FunctionalInterface
public interface LockTask<T> {
    /**
     * Gets a result.
     *
     * @return a result
     */
    T run() throws Exception;
}
