package org.smack.mono.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.smack.mono.common.services.RestService;
import org.smack.mono.configuration.AuthorizationConfiguration;
import org.smack.mono.domain.authorization.exceptions.AuthorizationException;
import org.smack.mono.domain.authorization.request.AuthRequest;
import org.smack.mono.domain.authorization.response.AuthResponse;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final AuthorizationConfiguration configuration;
    private final RestService restService;

    public void authorizeRequest(AuthRequest request) {
        if (!this.isRequestAuthorized(request)) {
            throw new AuthorizationException(request);
        }
    }

    protected boolean isRequestAuthorized(AuthRequest request) {
        if (!this.configuration.isEnabled()) {
            return true;
        }

        URI uri = this.constructUri();
        AuthResponse response = this.restService.post(uri, request, AuthResponse.class);

        return response.getResult();
    }

    private URI constructUri() {
        String url = configuration.getUrl();
        return URI.create(url);
    }
}
