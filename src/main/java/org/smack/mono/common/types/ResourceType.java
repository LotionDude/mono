package org.smack.mono.common.types;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {
    POST("post"),
    COMMENT("comment");

    @JsonValue
    private final String name;
}
