package org.smack.mono.domain.authorization.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
public class AuthResponse {
    @NonNull
    private final Boolean result;
}
