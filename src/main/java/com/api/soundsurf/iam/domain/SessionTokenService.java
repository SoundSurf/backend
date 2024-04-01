package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.SessionToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionTokenService {
    private final SessionTokenRepository repository;

    public SessionToken create(final Long userId) {
        final var newSessionToken = new SessionToken();

        newSessionToken.setUserId(userId);

        return repository.save(newSessionToken);
    }

    public Optional<SessionToken> findByUserId(final Long userId) {
        return repository.findByUserId(userId);
    }

    public String update(final String userUuid) {
        return null;
    }
}
