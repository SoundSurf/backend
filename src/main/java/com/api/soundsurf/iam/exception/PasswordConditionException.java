package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class PasswordConditionException extends ApiException {

    private final static String exceptionCode = ExceptionCode.USER.PASSWORD_CONDITION_ERROR;

    public PasswordConditionException() {
        super(exceptionCode);
    }
}
