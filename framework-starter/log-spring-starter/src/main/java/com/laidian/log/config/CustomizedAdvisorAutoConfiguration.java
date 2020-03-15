package com.laidian.log.config;

import com.laidian.log.properties.LogAopProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
