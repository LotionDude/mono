package org.smack.mono.domain.posts.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, String> {
}
