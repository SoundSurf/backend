package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class MusicAlreadySavedException extends ApiException {
    private final static String exceptionCode = ExceptionCode.SAVEDMUSIC.MUSIC_ALREADY_SAVED_EXCEPTION;

    public MusicAlreadySavedException(Long userId, String trackId) {
        super(exceptionCode + " : " + trackId);
    }
}
