package com.fattyca1.redis.properties;

import lombok.Data;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <br>锁属性设置</br>
 *
 * @author fattyca1
 */
@ConfigurationProperties(prefix = "redisson.lock")
@Data
@ConditionalOnBean(RedissonClient.class)
public class LockProperties {

    /**
     * 默认锁时间 20秒
     */
    private Integer lockTime = 20;
    /**
     * 获取锁最长等待时间 5秒
     */
    private Integer waitTime = 5;

    /** 锁类型 */
    private Boolean fair;
}
