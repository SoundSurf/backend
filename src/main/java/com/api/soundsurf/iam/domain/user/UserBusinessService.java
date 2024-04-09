package com.api.soundsurf.iam.domain.user;

import com.api.soundsurf.iam.domain.qr.QrTransferService;
import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.Car;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserProfile;
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

    public Long create(final String email, final String password, final UserProfile defaultUserProfile) {
        validateCreate(email, password);

        final var encryptPassword = encryptPassword(password);
        final var qr = qrTransferService.create(email);

        final var newUser = new User(email, encryptPassword, qr, defaultUserProfile);

        return service.create(newUser);
    }

    public User login(final UserDto.Login.Request requestDto) {
        final var user = findByEmail(requestDto.getEmail());
        validatePassword(user, requestDto.getPassword());

        return user;
    }

    public User info(final Long id) {
        return service.findById(id);
    }

    public void setCar(final Long id, final Car car) {
        final var user = service.findById(id);
        user.setCar(car);

        service.update(user);
    }

    public Car getUserCar(final Long id) {
        return service.findById(id).getCar();
    }

    public User getUser(final Long id) {
        return service.findById(id);
    }

    public void updateProfileImage(final User user, final UserProfile newUserProfile) {
        user.setUserProfile(newUserProfile);
        service.update(user);
    }

    private User findByEmail(final String email) {
        return service.findByEmail(email);
    }

    private void validatePassword(final User user, final String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchException();
        }
    }

    private void validateCreate(final String email, final String password) {
        validateNoDuplicateEmail(email);
        validatePasswordHaveEngAndDigit(password);
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

    private String encryptPassword(final String password) {
        return passwordEncoder.encode(password);
    }

    public String setNickname(final Long userId, final String nickname) {
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
