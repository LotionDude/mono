package org.smack.mono.domain.posts;

import lombok.RequiredArgsConstructor;
import org.smack.mono.common.services.MarkdownService;
import org.smack.mono.common.types.ResourceType;
import org.smack.mono.domain.authorization.AuthorizationService;
import org.smack.mono.domain.authorization.request.AuthRequest;
import org.smack.mono.domain.posts.entity.Post;
import org.smack.mono.domain.posts.entity.PostJpaRepository;
import org.smack.mono.domain.posts.presentation.request.PostCreateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PostService {
    private final AuthorizationService authService;
    private final MarkdownService markdownService;

    private final PostJpaRepository postJpaRepository;

    public void createPost(PostCreateRequest request, String user) {
        this.authService.authorizeRequest(new AuthRequest(user, PostActions.CREATE, ResourceType.POST, user));
        String plainBody = this.markdownService.getPlainTextFromMarkdown(request.getMarkdownBody());

        Post post = Post.builder()
                .title(request.getTitle())
                .markdownBody(request.getMarkdownBody())
                .plainBody(plainBody)
                .tags(request.getTags())
                .author(user)
                .isDeleted(false)
                .date(new Date())
                .build();

        this.postJpaRepository.save(post);
    }
}
