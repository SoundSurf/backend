package com.api.soundsurf.iam.domain.userProfile;

import com.api.soundsurf.iam.entity.UserProfile;
import com.api.soundsurf.iam.exception.UserProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;

    public UserProfile findById(final Long id){
        return repository.findById(id).orElseThrow(() -> new UserProfileNotFoundException(id));
    }

    public UserProfile create(final UserProfile userProfile) {
        return repository.save(userProfile);
    }
}
