package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class SpotifyNowPlayingException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SPOTIFY.SPOTIFY_ALBUM_INFO_EXCEPTION;

    public SpotifyNowPlayingException(String message) {
        super(exceptionCode + " : " + message);
    }
}
