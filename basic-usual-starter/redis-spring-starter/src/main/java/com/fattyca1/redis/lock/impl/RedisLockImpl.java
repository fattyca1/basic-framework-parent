package com.fattyca1.redis.lock.impl;

import com.fattyca1.common.exception.UnhandledRedisException;
import com.fattyca1.common.function.SupplierWithException;
import com.fattyca1.redis.lock.RedisLock;
import com.fattyca1.redis.properties.LockProperties;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <br></br>
 *
 * @author fattyca1
 */
@Component
@AllArgsConstructor
@EnableConfigurationProperties({LockProperties.class})
public class RedisLockImpl  implements RedisLock {

    private RedissonClient redisson;
    private LockProperties lockProperties;


    @Override
    public <T> T lock(String resourceName, SupplierWithException<T> worker) throws Exception {
       return this.lock(resourceName, worker, 20L);
    }

    @Override
    public <T> T lock(String resourceName, SupplierWithException<T> worker, long lockTime) throws Exception {

//        redisson.getFairLock(resourceName);

        RLock lock = redisson.getLock(resourceName);

        boolean locked = lock.tryLock(lockProperties.getWaitTime(), lockProperties.getWaitTime(), TimeUnit.SECONDS);

        if (locked) {
            try {
                return worker.get();
            }finally {
                lock.unlock();
            }
        }

        throw new UnhandledRedisException("redis try lock failed.");
    }
}
