package com.fattyca1.http.config;

import com.fattyca1.http.component.RestTemplateWrapper;
import com.fattyca1.http.properties.HttpClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.Optional;

/**
 * <br>工厂</br>
 *
 * @author fattyca1
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "enable", matchIfMissing = true)
@RequiredArgsConstructor
public class HttpRestTemplateFactory {

    private final HttpClientProperties httpClientProperties;

    @Bean("innerRestTemplate")
    @Primary
    public RestTemplateWrapper innerRestTemplate(@Qualifier("innerHttpClient") CloseableHttpClient innerHttpClient) {
        return buildHttpRestTemplate(innerHttpClient);
    }

    @Bean("outerRestTemplate")
    public RestTemplateWrapper outerRestTemplate(@Qualifier("outerHttpClient") CloseableHttpClient outerHttpClient) {
        return buildHttpRestTemplate(outerHttpClient);
    }

    private RestTemplateWrapper buildHttpRestTemplate(CloseableHttpClient client) {
        RestTemplateWrapper restTemplate = new RestTemplateWrapper();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory(client);
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(httpClientProperties.getConnectionTimeOut());
        // 数据读取超时时间
        clientHttpRequestFactory.setReadTimeout(httpClientProperties.getSocketTimeOut());
        // 连接不够用的等待时间
        clientHttpRequestFactory.setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout());
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

    @Bean("outerHttpClient")
    public CloseableHttpClient outAcceptsUntrustedCertsHttpClient() {
        HttpHost proxy = null;
        if (StringUtils.isNotBlank(httpClientProperties.getHttpProxyHost()) && httpClientProperties.getHttpProxyPort() != null) {
            proxy = new HttpHost(httpClientProperties.getHttpProxyHost(), httpClientProperties.getHttpProxyPort());
        }
        return createHttpClient(proxy);
    }

    @Bean("innerHttpClient")
    public CloseableHttpClient innerAcceptsUntrustedCertsHttpClient() {
        return createHttpClient(null);
    }


    private CloseableHttpClient createHttpClient(HttpHost proxy) {
        HttpClientBuilder b = HttpClientBuilder.create();
        Optional.ofNullable(proxy).ifPresent(b::setProxy);
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        // 总连接数
        connMgr.setMaxTotal(httpClientProperties.getMaxTotalConnections());
        // 同路由的并发数
        connMgr.setDefaultMaxPerRoute(httpClientProperties.getMaxConnectionPerHost());
        b.setConnectionManager(connMgr);
        // 设置自定义重试次数
        HttpClientRetryHandler httpRetryHandler = new HttpClientRetryHandler(httpClientProperties.getRetryTimes());
        b.setRetryHandler(httpRetryHandler);
        return b.build();
    }

}
