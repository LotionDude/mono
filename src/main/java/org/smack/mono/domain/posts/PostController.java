package org.smack.mono.domain.posts;

import lombok.RequiredArgsConstructor;
import org.smack.mono.common.constants.HeaderConstants;
import org.smack.mono.common.types.ResourceType;
import org.smack.mono.domain.authorization.AuthorizationService;
import org.smack.mono.domain.authorization.request.AuthRequest;
import org.smack.mono.domain.posts.entity.Post;
import org.smack.mono.domain.posts.presentation.request.PostCreateRequest;
import org.smack.mono.domain.posts.presentation.request.PostEditRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final AuthorizationService authService;

    //TODO: Don't return Post. Return a custom response object.
    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest request, @RequestHeader(HeaderConstants.USER_ID) String user) {
        this.authService.authorizeRequest(new AuthRequest(user, PostActions.CREATE, ResourceType.POST, user));

        return this.postService.createPost(request, user);
    }

    //TODO: Don't return Post. Return a custom response object.
    @PatchMapping("/{id}")
    public Post editPost(@RequestBody PostEditRequest request, @RequestHeader(HeaderConstants.USER_ID) String user, @PathVariable("id") Long id) {
        this.authService.authorizeRequest(new AuthRequest(user, PostActions.EDIT, ResourceType.POST, this.postService.getPostAuthor(id)));

        return this.postService.editPost(request, id);
    }

    //TODO: Don't return Post. Return a custom response object.
    @DeleteMapping("/{id}")
    public Post deletePost(@RequestHeader(HeaderConstants.USER_ID) String user, @PathVariable("id") Long id) {
        this.authService.authorizeRequest(new AuthRequest(user, PostActions.DELETE, ResourceType.POST, this.postService.getPostAuthor(id)));

        return this.postService.deletePost(id);
    }
}
