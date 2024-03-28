package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.PasswordConditionException;
import com.api.soundsurf.iam.exception.NicknameDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserBusinessService {
    private final UserService service;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long create(final User user) {
        validateCreate(user);

        encryptPassword(user);

        //TODO: userProfile 만들기, 아랫줄 고치기
        user.setUserProfileId(1L);
        //TODO: userQr 만들기, 아릿줄 고치기
        user.setUserQrId(1L);

        return service.create(user);
    }

    private void validateCreate(final User user) {
        validateNoDuplicateNickname(user.getNickname());
        validatePasswordHaveEngAndDigit(user.getPassword());
    }

    private void validateNoDuplicateNickname(final String nickName) {
        if (service.countByNickname(nickName) > 0) {
            throw new NicknameDuplicateException(nickName);
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

    private void encryptPassword(final User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
