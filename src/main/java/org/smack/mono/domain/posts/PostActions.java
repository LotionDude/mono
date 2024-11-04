package org.smack.mono.domain.posts;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.smack.mono.domain.authorization.AuthorizationAction;

@RequiredArgsConstructor
@Getter(onMethod_ = @__(@Override))
public enum PostActions implements AuthorizationAction {
    CREATE("create"),
    DELETE("delete"),
    EDIT("edit"),
    UPVOTE("upvote");

    @JsonValue
    private final String name;
}
