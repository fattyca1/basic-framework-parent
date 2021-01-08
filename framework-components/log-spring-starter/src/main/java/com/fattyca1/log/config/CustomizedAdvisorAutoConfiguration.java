package com.fattyca1.log.config;

import com.fattyca1.log.properties.LogAopProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <br></br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/18
 * @since 1.0
 */
@EnableConfigurationProperties(LogAopProperties.class)
public class CustomizedAdvisorAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = LogAopProperties.PREFIX,
            name = "enabled",
            matchIfMissing = true)
    public AopLogExpressionPointcutAdvisor aopLogExpressionPointcutAdvisor(LogAopProperties properties) {
        return new AopLogExpressionPointcutAdvisor(properties);
    }
}
