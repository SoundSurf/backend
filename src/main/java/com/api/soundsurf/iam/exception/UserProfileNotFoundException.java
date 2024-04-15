package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class UserProfileNotFoundException extends ApiException {

    private final static String exceptionCode = ExceptionCode.USER_PROFILE.USER_PROFILE_NOT_FOUND_EXCEPTION;

    public UserProfileNotFoundException(final Long id) {
        super(exceptionCode + " : " + id);
    }
}
