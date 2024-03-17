package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class UsernameDuplicateException extends ApiException {

    private final static String exceptionCode = ExceptionCode.User.USERNAME_DUPLICATE_ERROR;

    public UsernameDuplicateException(final String username) {
        super(exceptionCode + " : " + username);
    }
}
