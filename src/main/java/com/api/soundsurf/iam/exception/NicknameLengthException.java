package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class NicknameLengthException extends ApiException {
    private final static String exceptionCode = ExceptionCode.USER.NICKNAME_LENGTH_ERROR;

    public NicknameLengthException(final String nickname) {
        super(exceptionCode + " : " + nickname);
    }
}
