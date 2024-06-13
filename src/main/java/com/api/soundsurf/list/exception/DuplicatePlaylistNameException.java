package com.api.soundsurf.list.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class DuplicatePlaylistNameException extends ApiException {

    private final static String exceptionCode = ExceptionCode.PLAYLIST.DUPLICATE_PLAYLIST_NAME_EXCEPTION;

    public DuplicatePlaylistNameException(String message) {
        super(exceptionCode + " : " + message);
    }
}
