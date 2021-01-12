package com.fattyca1.http.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <br>配置文件</br>
 *
 * @author fattyca1
 */
@Data
@Primary
@Configuration
@ConfigurationProperties(prefix = HttpClientProperties.PREFIX)
public class HttpClientProperties {

    public static final String PREFIX = "httpclient";
    /**
     * 开关
     */
    private boolean enable = true;
    /**
     * 重试次数
     */
    private Integer retryTimes = 3;
    /**
     * 数据读取超时时间
     */
    private Integer socketTimeOut = 5000;
    /**
     * 连接不够用的等待时间
     */
    private Integer connectionRequestTimeout = 1000;
    /**
     * 最大连接数
     */
    private Integer maxTotalConnections = 50;
    /**
     * 同路由的并发数
     */
    private Integer maxConnectionPerHost = 10;
    /**
     * 连接超时时间
     */
    private Integer connectionTimeOut = 1000;

    /**
     * 代码host
     */
    private String httpProxyHost;

    /**
     * 代理端口
     */
    private Integer httpProxyPort;


}