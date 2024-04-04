package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class QrNotFoundException extends ApiException {
    private final static String exceptionCode = ExceptionCode.QR.QR_NOT_FOUND_ERROR;

    public QrNotFoundException() {
        super(exceptionCode);
    }
}
