package org.smack.mono.domain.posts;

import lombok.RequiredArgsConstructor;
import org.smack.mono.common.services.MarkdownService;
import org.smack.mono.domain.posts.entity.Post;
import org.smack.mono.domain.posts.entity.PostJpaRepository;
import org.smack.mono.domain.posts.exceptions.PostNotFoundException;
import org.smack.mono.domain.posts.presentation.request.PostCreateRequest;
import org.smack.mono.domain.posts.presentation.request.PostEditRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MarkdownService markdownService;

    private final PostJpaRepository postJpaRepository;

    public Post createPost(PostCreateRequest request, String user) {
        String plainBody = this.markdownService.getPlainTextFromMarkdown(request.getMarkdownBody());

        Post post = Post.builder()
                .title(request.getTitle())
                .markdownBody(request.getMarkdownBody())
                .plainBody(plainBody)
                .tags(Optional.ofNullable(request.getTags()).orElse(Collections.emptyList()))
                .author(user)
                .isDeleted(false)
                .isApproved(false)
                .createdAt(ZonedDateTime.now())
                .build();

        return this.postJpaRepository.save(post);
    }

    @Transactional
    public Post editPost(PostEditRequest request, Long id) {
        Post post = this.getPostById(id);

        if (request.getTitle() != null) post.setTitle(request.getTitle());
        if (request.getTags() != null) post.setTags(request.getTags());
        if (request.getMarkdownBody() != null) {
            String plainBody = this.markdownService.getPlainTextFromMarkdown(request.getMarkdownBody());

            post.setMarkdownBody(request.getMarkdownBody());
            post.setPlainBody(plainBody);
        }

        return this.postJpaRepository.save(post);
    }

    @Transactional
    public Post deletePost(Long id) {
        Post post = this.getPostById(id);

        post.setIsDeleted(true);
        post.setDeletedAt(ZonedDateTime.now());

        return this.postJpaRepository.save(post);
    }

    public String getPostAuthor(Long id) {
        return this.getPostById(id).getAuthor();
    }

    public Post getPostById(Long id) {
        Optional<Post> post = this.postJpaRepository.findByIdAndIsNotDeleted(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }
}
