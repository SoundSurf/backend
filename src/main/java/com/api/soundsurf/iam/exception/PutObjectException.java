package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class PutObjectException extends ApiException {
    private final static String exceptionCode = ExceptionCode.S3.PUT_OBJECT_EXCEPTION;

    public PutObjectException(String message) {
        super(exceptionCode + message);
    }
}
