package org.smack.mono.domain.posts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.smack.mono.domain.comments.entity.Comment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 30000)
    private String markdownBody;

    @Column(nullable = false, length = 30000)
    private String plainBody;

    @Column(nullable = false, updatable = false)
    private String author;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean isDeleted;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean isApproved;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime deletedAt;

    @ElementCollection
    private List<String> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
