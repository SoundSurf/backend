package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.UserNotFoundException;
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
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public User findById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void update(User user) {
        repository.save(user);
    }

    public boolean existsByNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }
}
