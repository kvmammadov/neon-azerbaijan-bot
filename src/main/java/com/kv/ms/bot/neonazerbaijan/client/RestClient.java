package com.kv.ms.bot.neonazerbaijan.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
public class RestClient {

    private final RestTemplate restTemplate;

    public <T> ResponseEntity<List<T>> get(String url, ParameterizedTypeReference<List<T>> parameterizedTypeReference) {
        return restTemplate.exchange(url, GET, null, parameterizedTypeReference);
    }

    public <T> ResponseEntity <T> get(String url,Class<T>responseClass){
        return restTemplate.getForEntity(url,responseClass);
    }

    public void get(String url) {
        restTemplate.getForEntity(url, Void.class);
    }

    public <T> T getForObject(String url, Class<T> responseClass) {
        return restTemplate.getForObject(url, responseClass);
    }

    public <T> ResponseEntity<T> post(String url, Object request, Class<T> responseClass) {
        return restTemplate.postForEntity(url, request, responseClass);
    }
}
