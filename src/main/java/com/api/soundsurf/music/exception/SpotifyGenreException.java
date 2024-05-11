package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class SpotifyGenreException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SPOTIFY.SPOTIFY_GENRE_ERROR;

    public SpotifyGenreException(String message) {
        super(exceptionCode + " : " + message);
    }

}
