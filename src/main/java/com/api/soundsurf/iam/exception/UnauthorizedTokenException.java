package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class UnauthorizedTokenException extends ApiException {

    private final static String exceptionCode = ExceptionCode.API.UNAUTHORIZED_TOKEN_EXCEPTION;

    public UnauthorizedTokenException() {
        super(exceptionCode);
    }
}
