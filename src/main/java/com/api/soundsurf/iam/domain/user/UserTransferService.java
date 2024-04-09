package com.api.soundsurf.iam.domain.user;


import com.api.soundsurf.iam.domain.SessionTokenService;
import com.api.soundsurf.iam.domain.car.CarTransferService;
import com.api.soundsurf.iam.domain.userProfile.UserProfileTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTransferService {
    private final UserBusinessService businessService;
    private final UserProfileTransferService userProfileTransferService;
    private final CarTransferService carTransferService;
    private final SessionTokenService sessionTokenService;

    @Transactional
    public UserDto.Create.Response create(final UserDto.Create.Request requestDto) {
        final var defaultUserProfile = userProfileTransferService.getDefaultProfileImage();
        final var defaultCar = carTransferService.getDefaultCar();

        final var userId = businessService.create(requestDto.getEmail(), requestDto.getPassword(),  defaultUserProfile, defaultCar);
        final var sessionToken = sessionTokenService.create(userId);

        return new UserDto.Create.Response(sessionToken.getToken());
    }

    @Transactional
    public UserDto.Login.Response login(final UserDto.Login.Request requestDto) {
        final var user = businessService.login(requestDto);

        final var sessionToken = sessionTokenService.findOrCreateNew(user.getId());

        return new UserDto.Login.Response(sessionToken.getToken());
    }

    @Transactional
    public UserDto.SetNickname.Response setNickname(final SessionUser sessionUser, final UserDto.SetNickname.Request requestDto) {
        String nickname = businessService.setNickname(sessionUser.getUserId(), requestDto.getNickname());

        return new UserDto.SetNickname.Response(nickname);
    }

    public UserDto.Info.Response info(final SessionUser sessionUser) {
        final var userId = sessionUser.getUserId();

        final var userInfo = businessService.info(userId);
        final var userCar = userInfo.getCar();
        final var userProfile = userInfo.getUserProfile();

        return new UserDto.Info.Response(userInfo, userCar.getId(), userProfile.getId());
    }

}
