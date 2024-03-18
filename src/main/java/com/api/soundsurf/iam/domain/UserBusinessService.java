package com.api.soundsurf.iam.domain;

import com.api.soundsurf.api.BooleanDeleted;
import com.api.soundsurf.iam.exception.PasswordConditionException;
import com.api.soundsurf.iam.exception.UsernameDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserBusinessService {
    private final UserService service;
    private final BCryptPasswordEncoder passwordEncoder;

    public String create(final User user) {
        validateCreate(user);

        encryptPassword(user);
        return service.create(user);
    }

    private void validateCreate(final User user) {
        validateNoDuplicateUserName(user.getUsername());
        validatePasswordHaveEngAndDigit(user.getPassword());
    }

    private void validateNoDuplicateUserName(final String userName) {
        if (service.countByUsername(userName, BooleanDeleted.TRUE) > 0) {
            throw new UsernameDuplicateException(userName);
        }
    }

    private void validatePasswordHaveEngAndDigit(final String password) {
        final var letterPattern = Pattern.compile("[a-zA-Z]");
        final var digitPattern = Pattern.compile("[0-9]");
        final var specialCharPattern = Pattern.compile("[!@#$%^&*?]");

        final var hasLetter = letterPattern.matcher(password);
        final var hasDigit = digitPattern.matcher(password);
        final var hasSpecialChar = specialCharPattern.matcher(password);

        if (!hasLetter.find() || !hasDigit.find() || !hasSpecialChar.find()) {
            throw new PasswordConditionException();
        }
    }

    private void encryptPassword(final User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
