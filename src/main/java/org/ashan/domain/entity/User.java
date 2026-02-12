package org.ashan.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ashan.domain.converter.UserStatusConverter;
import org.ashan.domain.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_invitation_send",nullable = false)
    private boolean isInvitationSend;

    @Convert(converter = UserStatusConverter.class)
    @Column(nullable = false)
    private UserStatus status;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return status == user.status && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, status, createdAt, updatedAt);
    }

    @PrePersist
    protected void perPersist(){
        this.isInvitationSend    = false;
        this.status              = UserStatus.ACTIVE;
        this.createdAt           = LocalDateTime.now();
        this.updatedAt           = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
