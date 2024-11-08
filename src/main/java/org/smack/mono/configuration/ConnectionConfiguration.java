package org.smack.mono.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
public class ConnectionConfiguration {
    private final String url;
    private final Duration connectTimeout;
    private final Duration readTimeout;
    private final int maxAttempts = 1;
    private final Duration retryTimeInterval = Duration.of(2, ChronoUnit.SECONDS);
}
