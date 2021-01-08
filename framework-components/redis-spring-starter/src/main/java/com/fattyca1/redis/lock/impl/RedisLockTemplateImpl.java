package com.fattyca1.redis.lock.impl;

import com.fattyca1.redis.exception.LockException;
import com.fattyca1.redis.function.LockTask;
import com.fattyca1.redis.lock.RedisLockTemplate;
import com.fattyca1.redis.properties.LockProperties;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <br>用redisson实现锁模板</br>
 *
 * @author fattyca1
 */
@Component
@AllArgsConstructor
@EnableConfigurationProperties({LockProperties.class})
@ConditionalOnBean(RedissonClient.class)
public class RedisLockTemplateImpl implements RedisLockTemplate {

    private RedissonClient redisson;
    private LockProperties lockProperties;

    @Override
    public <T> T lock(String lockName, LockTask<T> task) throws Exception {
        return lock(lockName,task,lockProperties.getFair());
    }

    @Override
    public <T> T lock(String lockName, LockTask<T> task, boolean fair) throws Exception {
        return  lock(lockName, task, lockProperties.getLockTime(), fair);
    }

    @Override
    public <T> T lock(String lockName, LockTask<T> task, long lockTime, boolean fair) throws Exception {
        return lock(lockName, task, lockProperties.getWaitTime(),lockTime, fair);
    }

    @Override
    public <T> T lock(String lockName, LockTask<T> task, long waitTime, long lockTime, boolean fair) throws Exception {

        RLock lock = getLock(lockName, fair);

        boolean locked = lock.tryLock(waitTime, lockTime, TimeUnit.SECONDS);

        if (locked) {
            try {
                return task.run();
            } finally {
                if (lock != null && lock.isLocked())
                    lock.unlock();
            }
        }

        throw new LockException("redis try lock failed.");
    }

    private RLock getLock(String lockName, boolean fair) {
        return fair ? redisson.getFairLock(lockName) : redisson.getLock(lockName);
    }
}
