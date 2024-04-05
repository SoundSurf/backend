package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.domain.qr.QrTransferService;
import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.NicknameDuplicateException;
import com.api.soundsurf.iam.exception.PasswordConditionException;
import com.api.soundsurf.iam.exception.PasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserBusinessService {
    private final UserService service;
    private final QrTransferService qrTransferService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long create(final User user) {
        validateCreate(user);

        encryptPassword(user);

        //TODO: userProfile 만들기, 아랫줄 고치기
//        user.setUserProfileId(1L);
        
        final var userId = service.create(user);

        qrTransferService.create(userId);

        return userId;
    }

    public User login(final UserDto.Login.Request requestDto) {
        final var user = findByEmail(requestDto.getEmail());
        validatePassword(user, requestDto.getPassword());

        return user;
    }

    public User info(final Long id) {
        return service.getById(id);
    }

    private User findByEmail(final String email) {
        return service.findByEmail(email);
    }

    private void validatePassword(final User user, final String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchException();
        }
    }

    private void validateCreate(final User user) {
        validateNoDuplicateEmail(user.getEmail());
        validatePasswordHaveEngAndDigit(user.getPassword());
    }

    private void validateNoDuplicateEmail(final String email) {
        if (service.countByEmail(email) > 0) {
            throw new NicknameDuplicateException(email);
        }
    }

    private void validatePasswordHaveEngAndDigit(final String password) {
        final var letterPattern = Pattern.compile("[a-zA-Z]");
        final var digitPattern = Pattern.compile("[0-9]");

        final var hasLetter = letterPattern.matcher(password);
        final var hasDigit = digitPattern.matcher(password);

        if (!hasLetter.find() || !hasDigit.find()) {
            throw new PasswordConditionException();
        }
    }

    private void encryptPassword(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public String setNickname(Long userId, String nickname) {
        final User user = service.findById(userId);
        validateNoDuplicateNickname(nickname);
        user.setNickname(nickname);
        service.update(user);
        return nickname;
    }

    private void validateNoDuplicateNickname(final String nickname) {
        if (service.existsByNickname(nickname)) {
            throw new NicknameDuplicateException(nickname);
        }
    }
}
