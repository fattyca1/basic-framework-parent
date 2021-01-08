package com.fattyca1.redis.lock;

import com.fattyca1.redis.function.LockTask;

/**
 * <br>RedisLock</br>
 *
 * @author fattyca1
 */
public interface RedisLockTemplate {

    <T> T lock(String lockName, LockTask<T> task) throws Exception;

    <T> T lock(String lockName, LockTask<T> task, boolean fair) throws Exception;

    <T> T lock(String lockName, LockTask<T> task, long lockTime, boolean fair) throws Exception;

    <T> T lock(String lockName, LockTask<T> task, long waitTime, long lockTime, boolean fair) throws Exception;
}
