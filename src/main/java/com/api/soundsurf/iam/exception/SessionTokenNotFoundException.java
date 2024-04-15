package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class SessionTokenNotFoundException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SESSION_TOKEN.SESSION_TOKEN_NOT_FOUND_ERROR;

    public SessionTokenNotFoundException() {
        super(exceptionCode);
    }

}
