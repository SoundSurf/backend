package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class CannotUploadImageException extends ApiException {
    private final static String exceptionCode = ExceptionCode.S3.CANNOT_UPLOAD_IMAGE_EXCEPTION;

    public CannotUploadImageException() {
        super(exceptionCode);
    }
}
