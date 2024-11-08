package org.smack.mono.domain.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smack.mono.common.retry.RetryableRestTemplate;
import org.smack.mono.configuration.AuthorizationConfiguration;
import org.smack.mono.domain.authorization.exceptions.AuthorizationException;
import org.smack.mono.domain.authorization.request.AuthRequest;
import org.smack.mono.domain.authorization.response.AuthResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final AuthorizationConfiguration configuration;

    @Qualifier("authorizationRestTemplate")
    private final RetryableRestTemplate restTemplate;

    // TODO: Log resource id?
    public void authorizeRequest(AuthRequest request) {
        if (!this.isRequestAuthorized(request)) {
            log.warn("User {} unauthorized for action {} on resource type {}.", request.getUsername(), request.getAction().getName(), request.getResourceType().getName());
            throw new AuthorizationException(request);
        }
        log.info("User {} authorized for action {} on resource type {}.", request.getUsername(), request.getAction().getName(), request.getResourceType().getName());
    }

    protected boolean isRequestAuthorized(AuthRequest request) {
        if (!this.configuration.isEnabled()) {
            return true;
        }

        URI uri = this.constructUri();
        AuthResponse response = this.restTemplate.post(uri, request, AuthResponse.class);

        return response.getResult();
    }

    private URI constructUri() {
        String url = this.configuration.getConnection().getUrl();
        return URI.create(url);
    }
}
