package org.smack.mono.domain.comments.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.isDeleted = false")
    Optional<Comment> findByIdAndIsNotDeleted(@Param("id") Long id);
}
