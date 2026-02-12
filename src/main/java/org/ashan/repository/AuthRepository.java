package org.ashan.repository;

import org.ashan.domain.entity.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthClient, Long> {

    Optional<AuthClient> findByClientId(String id);
}
