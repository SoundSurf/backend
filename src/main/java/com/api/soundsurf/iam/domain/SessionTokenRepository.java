package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
    ArrayList<SessionToken> findAllByTokenAndCreatedAtBefore(String token, LocalDateTime localDateTime);


    Optional<SessionToken> findByUserId(Long userId);
}
