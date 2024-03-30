package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Long create(final User user) {
        return repository.save(user).getId();
    }

    public Integer countByEmail(final String email) {
        return repository.countAllByEmail(email);
    }

    public User findByEmail(final String email) {
        return repository.getByEmailOrThrow(email);
    }
}
