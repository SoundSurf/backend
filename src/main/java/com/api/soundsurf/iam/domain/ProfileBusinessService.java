package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileBusinessService {
    private final ProfileService service;
    private final UserRepository userRepository;

    public String create(Long userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return service.create(user, nickname);
    }

}
