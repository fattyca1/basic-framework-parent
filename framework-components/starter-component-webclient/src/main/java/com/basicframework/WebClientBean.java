package com.basicframework;

import com.basicframework.config.HttpClient5Config;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <p></p>
 *
 * @author fattycal@qq.com
 * @since 2022/4/28
 */
public class WebClientBean {

    public WebClient webClient(){
        ClientHttpConnector connector = new HttpComponentsClientHttpConnector(HttpClient5Config.init());
        return WebClient.builder()
                .clientConnector(connector)
                .build();
    }
}
