package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class UserNotFoundException extends ApiException {

    private final static String exceptionCode = ExceptionCode.USER.USER_NOT_FOUND_ERROR;

    public UserNotFoundException(String email) {super(exceptionCode + " : " + email);}
}
