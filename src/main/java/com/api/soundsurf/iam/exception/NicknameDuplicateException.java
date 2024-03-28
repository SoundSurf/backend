package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class NicknameDuplicateException extends ApiException {

    private final static String exceptionCode = ExceptionCode.USER.NICKNAME_DUPLICATE_ERROR;

    public NicknameDuplicateException(final String nickname) {
        super(exceptionCode + " : " + nickname);
    }
}
