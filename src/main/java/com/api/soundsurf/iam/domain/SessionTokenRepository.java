package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
}
