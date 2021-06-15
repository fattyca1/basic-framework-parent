package com.fattyca1.http.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * <br>日志拦截器</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private final long timeoutMs;

    public LoggingRequestInterceptor(long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        long cost = System.currentTimeMillis() - start;
        if (cost > timeoutMs) {
            log.warn("Request uri: [{}], Cost times: {}ms", request.getURI(), cost);
        }
        trace(request, body, response);
        return response;
    }

    private void trace(HttpRequest request, byte[] body, ClientHttpResponse response){
        // 记录日志
    }

}
