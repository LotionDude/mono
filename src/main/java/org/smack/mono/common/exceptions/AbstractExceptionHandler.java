package org.smack.mono.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractExceptionHandler {

    protected ResponseEntity<ApiException> createResponse(HttpStatus status, Exception exception) {
        return this.createResponse(status, exception.getMessage());
    }

    protected ResponseEntity<ApiException> createResponse(HttpStatus status, String message) {
        ApiException response = new ApiException(status, message);
        return new ResponseEntity<>(response, status);
    }
}
