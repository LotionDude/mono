package org.smack.mono.domain.posts.exceptions;

import org.smack.mono.common.exceptions.AbstractExceptionHandler;
import org.smack.mono.common.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiException> handlePostNotFound(PostNotFoundException ex) {
        return this.createResponse(HttpStatus.BAD_REQUEST, ex);
    }
}
