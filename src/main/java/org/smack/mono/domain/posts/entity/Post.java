package org.smack.mono.domain.posts.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String markdownBody;
    private String plainBody;
    private String author;
    private Date date;
    private Boolean isDeleted;

    @ElementCollection
    private List<String> tags;
}
