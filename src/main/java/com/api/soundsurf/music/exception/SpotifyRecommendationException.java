package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class SpotifyRecommendationException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SPOTIFY.SPOTIFY_RECOMMENDATION_ERROR;

    public SpotifyRecommendationException(String message) {
        super(exceptionCode + " : " + message);
    }
}
