package com.api.soundsurf.iam.domain;


import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.UserCarNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTransferService {
    private final UserBusinessService businessService;
    private final SessionTokenService sessionTokenService;

    @Transactional
    public UserDto.Create.Response create(final UserDto.Create.Request requestDto) {
        final var user = new User();
        hydrateNewUser(requestDto, user);

        businessService.create(user);
        final var sessionToken = sessionTokenService.create(user.getId());

        return new UserDto.Create.Response(sessionToken.getToken());
    }

    @Transactional
    public UserDto.Login.Response login(final UserDto.Login.Request requestDto) {
        final var user = businessService.login(requestDto);

        final var sessionToken = sessionTokenService.findOrCreateNew(user.getId());

        return new UserDto.Login.Response(sessionToken.getToken());
    }

    public UserDto.Info.Response info(final SessionUser sessionUser) {
        final var userId= sessionUser.getUserId();

        final var userInfo = businessService.info(userId);
        final var userCar = userInfo.getCar();
        final var userProfile = userInfo.getUserProfile();

        if(userCar == null){
            throw new UserCarNotFoundException(userInfo.)
        }

        return new UserDto.Info.Response(userInfo, userCar.getId(), userProfile.getId());
    }

    private void hydrateNewUser(final UserDto.Create.Request requestDto, final User user) {
        user.setPassword(requestDto.getPassword());
        user.setEmail(requestDto.getEmail());
    }

}
