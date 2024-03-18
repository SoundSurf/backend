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

    public UserDto.Login.Response login(final UserDto.Login.Request requestDto) {
        //TODO: 로그인, sessionToken 검사, 비밀번호가 맞는데 sessionToken 만료됐으면 업데이트, 락 검사, 락 시키기, 성공하면 세팅 값 리턴하기, 토큰 리턴하기
        return null;
    }

    private void hydrateNewUser(final UserDto.Create.Request requestDto, final User user) {
        user.setUsername(requestDto.getUsername());
        user.setPassword(requestDto.getPassword());
        user.setEmail(requestDto.getEmail());
    }

}
