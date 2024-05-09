package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class SpotifyRecommendationSeedException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SPOTIFY.SPOTIFY_SEED_EXCEPTION;

    public SpotifyRecommendationSeedException() {
        super(exceptionCode);
    }
}
