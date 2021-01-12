package com.fattyca1.http.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * <br>Http调用模块</br>
 *
 * @author fattyca1
 */
@Slf4j
public class HttpRestTemplate extends RestTemplate {
/**
     * 定制化  返回类型： {@link Result <T>}
     * @param url
     * @param object
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> Result<T> postOfResult(String url, Object object, ResultParameterizedTypeReference<T> typeReference) {
        return this.post(url, object, typeReference);
    }

    private static class ResultParameterizedTypeReference<T> extends ParameterizedTypeReference<Result<T>>{
        private ResultParameterizedTypeReference(){
        }
    }

    /**
     * 发送http请求
     *
     * @param url           the url
     * @param method        the method
     * @param typeReference responseType
     * @param <T>           Type generic
     * @return
     * @throws RestClientException
     */
    public <T> ResponseEntity<T> request(String url, HttpMethod method, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> typeReference) throws RestClientException {
        long start = System.currentTimeMillis();
        ResponseEntity<T> entity = null;
        try {
            entity = super.exchange(url, method, httpEntity, typeReference);
        } catch (IllegalStateException e) {
            log.error("Request error. url: [{}], param:[{}]. ", url, JsonUtils.toJson(httpEntity.getBody()), e);
            throw new BizException(e.getMessage());
        }finally {
            log.info("【RestTemplate response】【time: {}ms】. url: [{}], param:[{}], ret:[{}]", (System.currentTimeMillis() - start), url, StringUtils.abbreviate(JsonUtils.toJson(httpEntity.getBody()), 1000),
                    Optional.ofNullable(entity).map(HttpEntity::getBody).map(JsonUtils::toJson).orElse(""));
        }
        // 一定不为空
        return entity;
    }

    public <T> T postForJson(String url, Object object, Class<T> responseType) throws RestClientException {
        return (T) post(url, object, ParameterizedTypeReference.forType(responseType));
    }

    public <T> T post(String url, Object object, ParameterizedTypeReference<T> typeReference) throws RestClientException {
        return post(url, object, null, typeReference);
    }

    public <T> T post(String url, Object object, HttpHeaders headers, ParameterizedTypeReference<T> typeReference) throws RestClientException {
        String requestJson = JsonUtils.toJson(object);
        HttpHeaders innerHeaders = Optional.ofNullable(headers).orElseGet(HttpHeaders::new);
        if (innerHeaders.getContentType() == null) {
            innerHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> entities = new HttpEntity<>(requestJson, innerHeaders);
        return request(url, HttpMethod.POST, entities, typeReference).getBody();
    }


    public <T> T postForm(String url, Map<String, Object> map, HttpHeaders headers, ParameterizedTypeReference<T> typeReference) {
        HttpHeaders innerHeaders = Optional.ofNullable(headers).orElseGet(HttpHeaders::new);

        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        if (MapUtils.isNotEmpty(map)) {
            postParameters.setAll(map);
        }
        innerHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return request(url, HttpMethod.POST, new HttpEntity<>(postParameters, innerHeaders), typeReference).getBody();
    }

    public <T> T get(String url, Map<String, Object> params, ParameterizedTypeReference<T> reference) {
        return request(url, HttpMethod.GET, new HttpEntity<>(buildUrlParam(params), null), reference).getBody();
    }

    private MultiValueMap<String, Object> buildUrlParam(Map<String, Object> params) {
        MultiValueMap<String, Object> reqParams = new LinkedMultiValueMap<>();
        if (MapUtils.isNotEmpty(params)) {
            reqParams.setAll(params);
        }
        return reqParams;
    }
}
