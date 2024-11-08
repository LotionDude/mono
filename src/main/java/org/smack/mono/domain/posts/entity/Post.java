package org.smack.mono.domain.posts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.ZonedDateTime;
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

    private String title;
    private String markdownBody;
    private String plainBody;
    private String author;
    private Boolean isDeleted;

    private ZonedDateTime createdAt;
    private ZonedDateTime deletedAt;

    @ElementCollection
    private List<String> tags;
}
