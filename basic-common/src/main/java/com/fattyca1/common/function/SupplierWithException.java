package com.fattyca1.common.function;

/**
 * <br>函数接口</br>
 *
 * @author fattyca1
 */
public interface SupplierWithException<T> {
    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}
