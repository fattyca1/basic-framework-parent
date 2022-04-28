package com.basicframework.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * <p>http5配置</p>
 *
 * @author fattycal@qq.com
 * @since 2022/4/28
 */
public class HttpClient5Config {

    public static CloseableHttpAsyncClient init(){

        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofMilliseconds(250))          // 1.1
                .setSelectInterval(TimeValue.ofMilliseconds(50))    // 1.2
                .build();

        PoolingAsyncClientConnectionManager build = PoolingAsyncClientConnectionManagerBuilder.create()
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.LAX)  // 2.1
                .setMaxConnPerRoute(6).build();                       // 2.2

        // 设置Request 操作
        RequestConfig config = RequestConfig.copy(RequestConfig.DEFAULT)
                .setConnectTimeout(150, TimeUnit.MILLISECONDS)              // 4.1
                .setConnectionRequestTimeout(200, TimeUnit.MILLISECONDS)    // 4.2
                .setResponseTimeout(100, TimeUnit.MILLISECONDS)
                .build();    // 4.3


        return HttpAsyncClients.custom()
                .setIOReactorConfig(ioReactorConfig)
                .setConnectionManager(build)
                .disableAutomaticRetries()       // 3.1
                .setDefaultRequestConfig(config)
                .build();


    }
}
