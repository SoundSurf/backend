package com.api.soundsurf.api.exception;

public class DbConnectionCredentialException extends ApiException {

    private final static String exceptionCode = ExceptionCode.Api.CONNECTION_CREDENTIAL_ERROR;
    public DbConnectionCredentialException() {
        super(exceptionCode);
    }
}
