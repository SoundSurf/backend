package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class UserCarNotFoundException extends ApiException {

    private final static String exceptionCode = ExceptionCode.USER.USER_CAR_NOT_MATCH_ERROR;

    public UserCarNotFoundException(Long id) {super(exceptionCode + " : " + id);}
}
