package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class QrExistException extends ApiException {

    private final static String exceptionCode = ExceptionCode.QR.QR_ALREADY_EXIST_ERROR;

    public QrExistException() {
        super(exceptionCode);
    }
}

