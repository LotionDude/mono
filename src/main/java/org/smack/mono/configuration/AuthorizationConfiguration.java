package org.smack.mono.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("authorization")
public class AuthorizationConfiguration {
    private final String serviceName;
    private final ConnectionConfiguration connection;

    /**
     * Should the authorization server be called? When disabled, will authorize all requests.
     * Dangerous for production, useful for local development.
     */
    private final boolean enabled;
}
