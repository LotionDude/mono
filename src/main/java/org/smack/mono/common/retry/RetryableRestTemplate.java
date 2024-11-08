package org.smack.mono.common.retry;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

// TODO: Change class name? Doesn't necessarily have to retry if maxRetryAttempts is 1
@RequiredArgsConstructor
public class RetryableRestTemplate {
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public <REQ, RES> RES post(@NonNull URI url, @NonNull REQ request, @NonNull Class<RES> responseType) throws RestClientException {
        HttpHeaders headers = this.createDefaultHeaders();
        HttpEntity<REQ> entity = new HttpEntity<>(request, headers);

        return this.retryTemplate.execute(retryContext ->
                this.restTemplate.postForEntity(url, entity, responseType).getBody()
        );
    }

    protected HttpHeaders createDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
