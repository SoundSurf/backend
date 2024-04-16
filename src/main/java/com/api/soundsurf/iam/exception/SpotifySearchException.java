package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class SpotifySearchException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SPOTIFY.SPOTIFY_SEARCH_ERROR;

    public SpotifySearchException(String message) {
        super(exceptionCode + " : " + message);
    }
}
