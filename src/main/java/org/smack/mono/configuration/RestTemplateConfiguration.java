package org.smack.mono.configuration;

import lombok.RequiredArgsConstructor;
import org.smack.mono.common.retry.HttpStatusRetryBuilder;
import org.smack.mono.common.retry.RetryLoggerListener;
import org.smack.mono.common.retry.RetryableRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {
    private final AuthorizationConfiguration authorizationConfiguration;

    // TODO: Make this bean conditional if the service is enabled
    @Bean
    @Autowired
    @Qualifier("authorizationRestTemplate")
    protected RetryableRestTemplate getAuthorizationRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(this.authorizationConfiguration.getConnection().getReadTimeout())
                .setConnectTimeout(this.authorizationConfiguration.getConnection().getConnectTimeout())
                .build();
        RetryTemplate retryTemplate = this.defaultRetryBuilder()
                .withRetryInterval(this.authorizationConfiguration.getConnection().getRetryTimeInterval())
                .withMaxAttempts(this.authorizationConfiguration.getConnection().getMaxAttempts())
                .withRetryListener(new RetryLoggerListener(this.authorizationConfiguration.getServiceName()))
                .build();

        return new RetryableRestTemplate(restTemplate, retryTemplate);
    }

    protected HttpStatusRetryBuilder defaultRetryBuilder() {
        return new HttpStatusRetryBuilder()
                .withMaxAttempts(1)
                .withRetryInterval(Duration.of(1, ChronoUnit.SECONDS))
                .withException(ResourceAccessException.class)
                .withHttpStatus(HttpStatus.TOO_MANY_REQUESTS)
                .withHttpStatus(HttpStatus.BAD_GATEWAY)
                .withHttpStatus(HttpStatus.GATEWAY_TIMEOUT)
                .withHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
