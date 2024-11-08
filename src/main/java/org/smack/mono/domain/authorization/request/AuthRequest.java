package org.smack.mono.domain.authorization.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.smack.mono.common.types.ResourceType;
import org.smack.mono.domain.authorization.AuthorizationAction;

@Getter
@NonNull
@RequiredArgsConstructor
public class AuthRequest {
    private final String username;
    private final AuthorizationAction action;
    private final ResourceType resourceType;
    private final String resourceAuthor;
}
