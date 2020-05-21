package com.fattyca1.http.config;

import com.fattyca1.http.component.HttpRestTemplate;
import com.fattyca1.http.properties.HttpClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * <br>工厂</br>
 *
 * @author lifacheng
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "enable", matchIfMissing = true)
@RequiredArgsConstructor
public class HttpRestTemplateFactory {

    private final HttpClientProperties httpClientProperties;

    @Bean
    public HttpRestTemplate getHttpRestTemplate() {
        HttpRestTemplate restTemplate = new HttpRestTemplate();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory(acceptsUntrustedCertsHttpClient(httpClientProperties.getRetryTimes()));
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(httpClientProperties.getConnectionTimeOut());
        // 数据读取超时时间
        clientHttpRequestFactory.setReadTimeout(httpClientProperties.getSocketTimeOut());
        // 连接不够用的等待时间
        clientHttpRequestFactory.setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout());
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

    private CloseableHttpClient acceptsUntrustedCertsHttpClient(int retryTimes) {

        HttpClientBuilder b = HttpClientBuilder.create();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        // 总连接数
        connMgr.setMaxTotal(httpClientProperties.getMaxTotalConnections());
        // 同路由的并发数
        connMgr.setDefaultMaxPerRoute(httpClientProperties.getMaxConnectionPerHost());
        b.setConnectionManager(connMgr);
        // 设置自定义重试次数
        HttpClientRetryHandler httpRetryHandler = new HttpClientRetryHandler(retryTimes);
        b.setRetryHandler(httpRetryHandler);
        return b.build();
    }


}