package com.api.soundsurf.iam.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
    ArrayList<SessionToken> findAllByTokenAndCreatedAtBefore(String token, LocalDateTime localDateTime);
}
