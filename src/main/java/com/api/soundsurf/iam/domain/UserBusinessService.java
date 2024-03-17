package com.api.soundsurf.iam.domain;

import com.api.soundsurf.api.BooleanDeleted;
import com.api.soundsurf.iam.exception.UsernameDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBusinessService {
    private final UserService service;

    public String create(final User user) {
        validateCreate(user);
        return service.create(user);
    }

    private void validateCreate(final User user) {
        validateNoDuplicateUserName(user.getUsername());

    }

    private void validateNoDuplicateUserName(final String userName) {
        if (service.countByUsername(userName, BooleanDeleted.TRUE) > 0) {
            throw new UsernameDuplicateException(userName);
        }
    }

    private void validatePasswordCondition(final String password) {
        if
    }
}
