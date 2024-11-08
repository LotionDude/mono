package org.smack.mono.common.retry;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.smack.mono.common.utilities.SetUtilities;
import org.springframework.classify.Classifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;

@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpStatusRetryBuilder {

    @With(AccessLevel.PRIVATE)
    private final Set<HttpStatusCode> retryStatusCodes;

    @With(AccessLevel.PRIVATE)
    private final Set<Class<? extends Exception>> retryExceptions;

    private int maxAttempts;
    private Duration retryInterval;
    private RetryListener retryListener;

    public HttpStatusRetryBuilder() {
        this.maxAttempts = 1;
        this.retryInterval = Duration.of(1, ChronoUnit.SECONDS);
        this.retryStatusCodes = Collections.emptySet();
        this.retryExceptions = Collections.emptySet();
    }

    public HttpStatusRetryBuilder withHttpStatus(HttpStatus httpStatus) {
        return this.withRetryStatusCodes(SetUtilities.copyAndAdd(this.retryStatusCodes, httpStatus));
    }

    public HttpStatusRetryBuilder withException(Class<? extends Exception> exception) {
        return this.withRetryExceptions(SetUtilities.copyAndAdd(this.retryExceptions, exception));
    }

    public RetryTemplate build() {
        return this.build(new RetryTemplateBuilder());
    }

    public RetryTemplate build(RetryTemplateBuilder builder) {
        Assert.isTrue(this.maxAttempts >= 1, "Retry attempts must be greater than or equal to 1!");

        builder.customPolicy(this.createRetryPolicy());
        builder.fixedBackoff(this.retryInterval);
        builder.withListener(this.retryListener);

        return builder.build();
    }

    private RetryPolicy createRetryPolicy() {
        ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        policy.setExceptionClassifier(this.configurePolicy());

        return policy;
    }

    private Classifier<Throwable, RetryPolicy> configurePolicy() {
        return (throwable) -> {
            if (this.shouldHttpExceptionRetry(throwable) || this.shouldGeneralExceptionRetry(throwable)) {
                return new SimpleRetryPolicy(this.maxAttempts);
            }

            return new NeverRetryPolicy();
        };
    }

    private boolean shouldHttpExceptionRetry(Throwable throwable) {
        return throwable instanceof HttpStatusCodeException httpException && this.retryStatusCodes.contains(httpException.getStatusCode());
    }

    private boolean shouldGeneralExceptionRetry(Throwable throwable) {
        return this.retryExceptions.stream().anyMatch(ex -> ex.isInstance(throwable));
    }
}
