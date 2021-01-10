package com.fattyca1.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <br>属性配置</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/16
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = LogProperties.PREFIX)
@Component
public class LogProperties {

    public static final String PREFIX = "c.log";

    private List<LogConfig> config = Collections.singletonList(new LogConfig());

    // 默认切入点
    private static final String DEFAULT_EXPRESSION = "execution(public * com.test.*..*(..))";

    @Data
    public static class LogConfig {
        /**
         * 前端调用打印头消息
         */
        private boolean logHeader;

        /**
         * 是否是web请求
         */
        private boolean web;

        /**
         * 入参长度 (文件特别长要注意)
         */
        private Integer len = 1000;

        /**
         * 切入点表达式
         */
        private String pointCut = DEFAULT_EXPRESSION;
    }
}
