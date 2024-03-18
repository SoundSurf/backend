package com.api.soundsurf.iam.domain;


import com.api.soundsurf.iam.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTransferService {
    private final UserBusinessService businessService;

    public UserDto.Create.Response create(final UserDto.Create.Request requestDto) {
        final var user = new User();
        hydrateNewUser(requestDto, user);

        final var newUserUuid = businessService.create(user);

        return new UserDto.Create.Response(newUserUuid);
    }

    private void hydrateNewUser(final UserDto.Create.Request requestDto, final User user) {
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
    }

}
