package com.api.soundsurf.iam.domain.userProfile;

import com.api.soundsurf.api.utils.StringByteConverter;
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
    private final StringByteConverter stringByteConverter;

    public void upload(final SessionUser sessionUser, final UserProfileDto.Image.Request request) {
        final var user = userBusinessService.getUser(sessionUser.getUserId());
        final var byteImage = stringByteConverter.stringToByte(request.getImgStr());

        businessService.upload(user, byteImage);
    }

    public UserProfile getDefaultProfileImage() {
        return service.findById(1L);
    }

    public UserProfileDto.Get.Response get(final SessionUser sessionUser) {
        final var user = userBusinessService.getUser(sessionUser.getUserId());
        final var userProfile = service.findById(user.getUserProfile().getId());
        final var profileString = stringByteConverter.byteToString(userProfile.getImage());

        return new UserProfileDto.Get.Response(profileString);
    }

}
