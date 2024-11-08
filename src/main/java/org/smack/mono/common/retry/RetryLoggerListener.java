package org.smack.mono.common.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Slf4j
@RequiredArgsConstructor
public class RetryLoggerListener implements RetryListener {
    private final String serviceName;

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.warn("Request Attempt {} on service {} failed due to {}", context.getRetryCount(), this.serviceName, throwable.toString());
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (throwable != null) {
            log.error("All retry attempts failed after {} attempts on service {}!", context.getRetryCount(), this.serviceName, throwable);
        }
    }
}
