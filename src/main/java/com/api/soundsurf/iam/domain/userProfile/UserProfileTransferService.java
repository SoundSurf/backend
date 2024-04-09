package com.api.soundsurf.iam.domain.userProfile;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.iam.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileTransferService {
    private final UserProfileBusinessService businessService;
    private final UserProfileService service;
    private final UserBusinessService userBusinessService;

    public void upload(final SessionUser sessionUser, final UserProfileDto.Image.Request request) {
        final var user = userBusinessService.getUser(sessionUser.getUserId());

        businessService.upload(user, request.getImage());
    }

    public UserProfile getDefaultProfileImage() {
        return service.findById(1L);
    }

}
