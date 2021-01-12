package com.fattyca1.http.config;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;

/**
 * <br>重试处理</br>
 *
 * @author fattyca1
 */
public class HttpClientRetryHandler implements HttpRequestRetryHandler {

    private int retryTimes;

    HttpClientRetryHandler(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    @Override
    public boolean retryRequest(IOException exception, int retryTimes, HttpContext context) {
        if (retryTimes > this.retryTimes) {
            return false;
        }
        if (!(exception instanceof SSLException)) {
            return true;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        // 如果请求被认为是幂等的，那么就重试。即重复执行不影响程序其他效果的
        return !(request instanceof HttpEntityEnclosingRequest);
    }

}