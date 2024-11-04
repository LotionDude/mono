package org.smack.mono.domain.authorization.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    @NonNull
    private final Boolean result;
}
