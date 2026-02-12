package org.ashan.repository;

import jakarta.transaction.Transactional;
import org.ashan.domain.entity.Comment;
import org.ashan.domain.entity.User;
import org.ashan.domain.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Transactional
    @Query("""
        UPDATE Comment c
        SET c.isVisible = :status
        WHERE c.id = :id and c.post.user = :user
    """)
    int updateCommentStatus(@Param("id") Long id,
                            @Param("status")CommentStatus status,
                            @Param("user") User user);

    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.post p
        WHERE c.user.id = :id
    """)
    List<Comment> getUserRelatedComments(@Param("id") Long id);
}
