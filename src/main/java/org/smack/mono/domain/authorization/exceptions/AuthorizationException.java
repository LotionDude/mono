package org.smack.mono.domain.authorization.exceptions;

import lombok.Getter;
import org.smack.mono.domain.authorization.request.AuthRequest;

@Getter
public class AuthorizationException extends RuntimeException {
    private final AuthRequest request;

    public AuthorizationException(AuthRequest request) {
        super(String.format(
                "User %s is unauthorized for action [%s] on author's [%s] resource, of type [%s]!",
                request.getUsername(),
                request.getAction().getName(),
                request.getResourceAuthor(),
                request.getResourceType()
        ));
        this.request = request;
    }
}
