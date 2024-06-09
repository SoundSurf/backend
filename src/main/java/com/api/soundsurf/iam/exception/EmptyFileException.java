package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class EmptyFileException extends ApiException {
    private final static String exceptionCode = ExceptionCode.S3.EMPTY_FILE_EXCEPTION;

    public EmptyFileException() {
        super(exceptionCode);
    }
}
