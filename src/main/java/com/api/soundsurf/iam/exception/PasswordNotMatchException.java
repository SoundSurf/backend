package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class PasswordNotMatchException extends ApiException {

    private final static String exceptionCode = ExceptionCode.USER.PASSWORD_NOT_MATCH_ERROR;

    public PasswordNotMatchException() {
        super(exceptionCode);
    }
}
