package com.fattyca1.redis.lock;

import com.fattyca1.common.function.SupplierWithException;

/**
 * <br>RedisLock</br>
 *
 * @author fattyca1
 */
public interface RedisLock {

    /**
     * 加锁默认20秒
     *
     * @param resourceName
     * @param worker
     * @throws Exception
     */
    <T> T lock(String resourceName, SupplierWithException<T> worker) throws Exception;


    /**
     * 加锁时间
     *
     * @param resourceName
     * @param worker
     * @param lockTime
     * @return
     * @throws Exception
     */
    <T> T lock(String resourceName, SupplierWithException<T> worker, long lockTime) throws Exception;
}
