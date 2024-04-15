package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class CannotCreateQRException extends ApiException {

    private final static String exceptionCode = ExceptionCode.QR.CANNOT_CREATE_QR_ERROR;

    public CannotCreateQRException(String message) {
        super(exceptionCode + " : " + message);
    }
}
