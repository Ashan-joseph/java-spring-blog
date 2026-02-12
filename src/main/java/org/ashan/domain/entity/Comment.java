package org.ashan.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ashan.domain.converter.CommentStatusConverter;
import org.ashan.domain.enums.CommentStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Post post;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String comment;

    @Convert(converter = CommentStatusConverter.class)
    @Column(name="is_visible",nullable = false)
    private CommentStatus isVisible;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return isVisible == comment1.isVisible && Objects.equals(id, comment1.id) && Objects.equals(comment, comment1.comment) && Objects.equals(createdAt, comment1.createdAt) && Objects.equals(updatedAt, comment1.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, isVisible, createdAt, updatedAt);
    }

    @PrePersist
    protected void perPersist(){
        this.isVisible = CommentStatus.VISIBLE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
