package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class MusicNotFoundException extends ApiException {
    private final static String exceptionCode = ExceptionCode.MUSIC.MUSIC_NOT_FOUND_EXCEPTION;

    public MusicNotFoundException(String trackId) {
        super(exceptionCode + " : " + trackId);
    }

    public MusicNotFoundException(Long trackId) {
        super(exceptionCode + " : " + trackId);
    }

}
