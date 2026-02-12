package org.ashan.repository;

import org.ashan.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
            SELECT DISTINCT p
            FROM Post p
            WHERE p.status = 1
        """)
    List<Post> findAllActivePosts();

    @Query("""
        SELECT DISTINCT p
        FROM Post p
        JOIN FETCH p.user
        LEFT JOIN FETCH p.comments c
        LEFT JOIN FETCH c.user
        WHERE p.id = :id AND (c is NULL or c.isVisible = 1)
    """)
    Optional<Post> getPostById(@Param("id") Long id);
}
