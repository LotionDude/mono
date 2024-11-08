package org.smack.mono.domain.posts.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.isDeleted = false")
    Optional<Post> findByIdAndIsNotDeleted(@Param("id") Long id);
}
