package org.smack.mono.domain.authorization.exceptions;

import org.smack.mono.common.exceptions.AbstractExceptionHandler;
import org.smack.mono.common.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiException> handlePostNotFound(AuthorizationException ex) {
        return this.createResponse(HttpStatus.UNAUTHORIZED, ex);
    }
}
