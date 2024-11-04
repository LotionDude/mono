package org.smack.mono.domain.posts;

import lombok.RequiredArgsConstructor;
import org.smack.mono.common.constants.HeaderConstants;
import org.smack.mono.domain.posts.presentation.request.PostCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public void createPost(PostCreateRequest request, @RequestHeader(HeaderConstants.USER_ID) String user) {
        this.postService.createPost(request, user);
    }
}
