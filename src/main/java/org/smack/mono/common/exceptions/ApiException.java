package org.smack.mono.common.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApiException {
    @JsonIgnore
    private final HttpStatus status;
    private final String message;

    @JsonProperty("status")
    public int getStatusCode() {
        return this.status.value();
    }
}
