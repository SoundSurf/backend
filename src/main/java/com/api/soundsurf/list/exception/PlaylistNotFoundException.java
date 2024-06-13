package com.api.soundsurf.list.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class PlaylistNotFoundException extends ApiException {

    private final static String exceptionCode = ExceptionCode.PLAYLIST.DUPLICATE_PLAYLIST_NAME_EXCEPTION;

    public PlaylistNotFoundException(Long id) {
        super(exceptionCode + " : " + id);
    }
}
