package org.ashan.repository;

import jakarta.transaction.Transactional;
import org.ashan.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    User findFirstByIsInvitationSendFalse();

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isInvitationSend = true WHERE u.id = :id")
    int updateInvitationStatus(@Param("id") Long id);
}
