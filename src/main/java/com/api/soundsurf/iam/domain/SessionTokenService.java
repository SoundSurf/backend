package com.api.soundsurf.iam.domain;

import com.api.soundsurf.api.BooleanDeleted;
import com.api.soundsurf.iam.entity.SessionToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionTokenService {
    private final SessionTokenRepository repository;

    public SessionToken findByUserUuid(final String userUuid) {
        return null;
    }

    public String update(final String userUuid){
        return null;
    }
}
