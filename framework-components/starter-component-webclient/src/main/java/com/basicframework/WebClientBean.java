package com.basicframework;

import com.basicframework.config.HttpClient5Config;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * <p>初始化webClient</p>
 *
 * @author fattycal@qq.com
 * @since 2022/4/28
 */
public class WebClientBean {

    public WebClient webClient(){

        ClientHttpConnector connector = new HttpComponentsClientHttpConnector(HttpClient5Config.init());
        return WebClient.builder()
                .clientConnector(connector)
                .filter(logRequest())
                .build();
    }

    private ExchangeFilterFunction logRequest(){
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            clientRequest.body();
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse(){
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            return Mono.just(clientResponse);
        });
    }
}
