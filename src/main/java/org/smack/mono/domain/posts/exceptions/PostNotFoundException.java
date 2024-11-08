package org.smack.mono.domain.posts.exceptions;

import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {
    private final Long id;

    public PostNotFoundException(Long id) {
        super(String.format("Could not find post with id '%s'!", id));
        this.id = id;
    }
}
