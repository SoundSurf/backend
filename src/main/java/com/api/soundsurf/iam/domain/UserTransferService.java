package com.api.soundsurf.iam.domain;


import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTransferService {
    private final UserBusinessService businessService;

    public UserDto.Create.Response create(final UserDto.Create.Request requestDto) {
        final var user = new User();
        hydrateNewUser(requestDto, user);

        final var newUserId = businessService.create(user);

        return new UserDto.Create.Response(newUserId);
    }

    private void hydrateNewUser(final UserDto.Create.Request requestDto, final User user) {
        user.setPassword(requestDto.getPassword());
        user.setEmail(requestDto.getEmail());
    }

}
