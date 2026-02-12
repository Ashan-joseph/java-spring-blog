package org.ashan.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name="auth_clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="client_id")
    private String clientId;

    @Column(name = "secret")
    private String secret;

    @Column(name="status")
    private boolean status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthClient that = (AuthClient) o;
        return status == that.status && Objects.equals(id, that.id) && Objects.equals(clientId, that.clientId) && Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, secret, status);
    }
}
