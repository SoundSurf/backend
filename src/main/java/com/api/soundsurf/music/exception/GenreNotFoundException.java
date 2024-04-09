package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class GenreNotFoundException extends ApiException {

    private final static String exceptionCode = ExceptionCode.GENRE.GENRE_NOT_FOUND_EXCEPTION;

    public GenreNotFoundException(Long id) {
        super(exceptionCode + " : " + id);
    }
}
