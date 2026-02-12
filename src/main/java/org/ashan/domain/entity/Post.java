package org.ashan.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ashan.domain.converter.PostStatusConverter;
import org.ashan.domain.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Convert(converter = PostStatusConverter.class)
    @Column(nullable = false)
    private PostStatus status;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return status == post.status && Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(description, post.description) && Objects.equals(createdAt, post.createdAt) && Objects.equals(updatedAt, post.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, createdAt, updatedAt);
    }

    @PrePersist
    protected void perPersist(){
        this.status    = PostStatus.HIDE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
