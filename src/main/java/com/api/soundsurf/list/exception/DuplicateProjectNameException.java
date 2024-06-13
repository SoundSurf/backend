package com.api.soundsurf.list.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class DuplicateProjectNameException extends ApiException {

    private final static String exceptionCode = ExceptionCode.PROJECT.DUPLICATE_PROJECT_NAME_EXCEPTION;

    public DuplicateProjectNameException(String message) {
        super(exceptionCode + " : " + message);
    }
}
