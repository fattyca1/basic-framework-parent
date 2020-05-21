package com.fattyca1.http.component;

import com.fattyca1.common.util.JSONUtils;
import com.fattyca1.common.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * <br>Http调用模块</br>
 *
 * @author lifacheng
 */
@Slf4j
public class HttpRestTemplate extends RestTemplate {

    public <T> T postForJson(String url, Object object, Class<T> responseType)
            throws RestClientException {
        String requestJson = JSONUtils.toJson(object);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entities = new HttpEntity<>(requestJson, headers);
        return super.postForObject(url, entities, responseType);
    }

    public <T> T postForJsonStr(String url, String requestJson, Class<T> responseType)
            throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entities = new HttpEntity<>(requestJson, headers);
        return super.postForObject(url, entities, responseType);
    }

    public <T> T postForMap(String url, Map<String, Object> map, MediaType mediaType, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entities;
        if (mediaType == MediaType.APPLICATION_FORM_URLENCODED) {
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
            if (MapUtils.isNotEmpty(map)) {
                map.keySet().forEach(m -> postParameters.add(m, map.get(m)));
            }
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            entities = new HttpEntity<>(postParameters, headers);
        } else {
            String requestJson = JSONUtils.toJson(map);
            headers.setContentType(MediaType.APPLICATION_JSON);
            entities = new HttpEntity<>(requestJson, headers);
        }
        return super.postForObject(url, entities, clazz);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        return super.getForObject(url, responseType, uriVariables);
    }
}
