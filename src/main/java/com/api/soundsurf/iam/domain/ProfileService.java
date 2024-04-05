package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository repository;

    public String create(final User user, String nickname) {
        user.setNickname(nickname);
        return repository.save(user).getNickname();
    }
}
