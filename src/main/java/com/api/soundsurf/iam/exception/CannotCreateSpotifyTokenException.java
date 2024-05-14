package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class CannotCreateSpotifyTokenException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SPOTIFY.SPOTIFY_TOKEN_NOT_CREATED;

    public CannotCreateSpotifyTokenException(String message) {
        super(message + " : " + exceptionCode);
    }
}
