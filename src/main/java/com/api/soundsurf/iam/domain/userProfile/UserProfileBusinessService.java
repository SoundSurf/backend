package com.api.soundsurf.iam.domain.userProfile;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileBusinessService {
    private static UserProfileService service;
    private static UserBusinessService userBusinessService;

    public void upload(final User user, final byte[] image) {
        final var userProfile = new UserProfile();
        userProfile.setImage(image);

        final var newUserProfile = service.create(userProfile);

        userBusinessService.updateProfileImage(user, newUserProfile);
    }
}
