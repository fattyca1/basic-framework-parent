package com.basicframework.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fattyca1.common.exception.BizException;
import com.fattyca1.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br>Reactor 工具类</br>
 *
 * @author fattyca1@qq.com
 * @since 2022/5/13
 */
@Slf4j
public class Reactor {

    /**
     * 打印全流程日志，需要外部传入参数
     */
    public static Function<ClientResponse, Mono<String>> logging(String url, String method, Object reqBody) {
        return (clientResponse -> clientResponse.bodyToMono(String.class).doOnSuccess(body -> {
            if (log.isDebugEnabled()) {
                log.info("\n" +
                                "TraceId      : {}, {}\n" +
                                "URI          : {}, \n" +
                                "Param        : {}, \n" +
                                "RespHeader   : {}, \n" +
                                "RespStatus   : {}, \n" +
                                "Response     : {}", clientResponse.logPrefix(), method, url, JsonUtils.toJson(reqBody),
                        clientResponse.headers().asHttpHeaders(), clientResponse.rawStatusCode(),
                        StringUtils.abbreviate(body, 4000));
            }
        }));
    }

    /**
     * json 转化成 Mono<T>
     */
    public static <T> Function<String, Mono<T>> bodyExtra(TypeReference<T> typeReference) {
        return (body) -> Mono.justOrEmpty(JsonUtils.toObj(body, typeReference));
    }


    /**
     * 抛出业务异常
     *
     * @param errorCode 错误码
     */
    public static Consumer<? super Throwable> throwBizExp(String errorCode) {
        return throwable -> {
            throw new BizException(errorCode, throwable.getMessage());
        };
    }

    /**
     * 打印错误消息
     *
     * @param customizedErrorMsg 消息前缀
     */
    public static Consumer<? super Throwable> loggingErr(String customizedErrorMsg) {
        return throwable -> {
            log.error(customizedErrorMsg + ". Reason: {}", throwable.getMessage());
        };
    }
}