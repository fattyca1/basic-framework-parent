package com.fattyca1.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <br>锁属性设置</br>
 *
 * @author fattyca1
 */
@ConfigurationProperties(prefix = "redisson.lock")
@Data
public class LockProperties {

    /**
     * 默认锁时间 20秒
     */
    private Integer lockTime = 20;
    /**
     * 获取锁最长等待时间 5秒
     */
    private Integer waitTime = 5;
}
