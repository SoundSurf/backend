package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class PlaylistNotFoundException extends ApiException {
        private final static String exceptionCode = ExceptionCode.PLAYLIST.PLAYLIST_NOT_FOUND;

        public PlaylistNotFoundException(Long id) {
            super(exceptionCode + " : " + id);
        }
}
