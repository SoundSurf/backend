package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.SessionToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionTokenService {
    private final SessionTokenRepository repository;

    public SessionToken create(final Long userId) {
        final var newSessionToken = new SessionToken();

        newSessionToken.setUserId(userId);

        return repository.save(newSessionToken);
    }

    public SessionToken findOrCreateNew(final Long userId) {
        return repository.findByUserId(userId)
                .orElseGet(() -> create(userId));
    }

    public String update(final String userUuid) {
        return null;
    }
}
