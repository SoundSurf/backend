package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class MusicNotFoundException extends ApiException {
    private final static String exceptionCode = ExceptionCode.MUSIC.MUSIC_NOT_FOUND_EXCEPTION;

    public MusicNotFoundException(Long id) {
        super(exceptionCode + " : " + id);
    }
}
