package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class InvalidFileExtensionException extends ApiException {
    private final static String exceptionCode = ExceptionCode.S3.INVALID_FILE_EXTENSION_EXCEPTION;

    public InvalidFileExtensionException() {
        super(exceptionCode);
    }
}
