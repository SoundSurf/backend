package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class UserGenreCountException extends ApiException {
    private final static String exceptionCode = ExceptionCode.USER_GENRE.USERGENRE_COUNT_LIMIT_ERROR;

    public UserGenreCountException() {
        super(exceptionCode);
    }
}
