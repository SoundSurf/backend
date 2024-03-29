package com.api.soundsurf.iam.domain;


import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.User;
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

    private void hydrateNewUser(final UserDto.Create.Request requestDto, final User user) {
        user.setPassword(requestDto.getPassword());
        user.setEmail(requestDto.getEmail());
    }

}
