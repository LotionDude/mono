package org.smack.mono.domain.posts.presentation.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostEditRequest {
    private final String title;
    private final String markdownBody;
    private final List<String> tags;
}
