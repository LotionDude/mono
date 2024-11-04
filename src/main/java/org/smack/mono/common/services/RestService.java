package org.smack.mono.common.services;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RestService {
    private final RestTemplate restTemplate;

    public <REQ, RES> RES post(URI uri, REQ request, Class<RES> responseClass) {
        HttpHeaders headers = this.createDefaultHeaders();
        HttpEntity<REQ> entity = new HttpEntity<>(request, headers);

        return this.restTemplate.postForEntity(uri, entity, responseClass).getBody();
    }

    private HttpHeaders createDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
