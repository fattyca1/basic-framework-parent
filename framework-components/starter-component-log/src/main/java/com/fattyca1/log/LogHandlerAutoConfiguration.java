package com.fattyca1.log;

import com.fattyca1.log.properties.LogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <br>自动装配</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@ConditionalOnBean(LogHandlerAutoConfiguration.Marker.class)
@EnableConfigurationProperties(LogProperties.class)
@Configuration
public class LogHandlerAutoConfiguration {

    public static class Marker{
    }

//    @Bean
//    @ConditionalOnProperty(value = "fc.log.enabled", matchIfMissing = true)
//    public LogHandlerConfig logHandlerConfig(){
//        return new LogHandlerConfig();
//    }
}
