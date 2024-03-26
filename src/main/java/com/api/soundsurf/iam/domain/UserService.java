package com.api.soundsurf.iam.domain;

import com.api.soundsurf.api.BooleanDeleted;
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

    public Integer countByUsername(final String userName, final BooleanDeleted deleted) {
        if (deleted.isTrue()) {
            return repository.countAllByUsername(userName);
        } else {
            return repository.countAllByUsernameAndDeletedIsFalse(userName);
        }

    }
}
