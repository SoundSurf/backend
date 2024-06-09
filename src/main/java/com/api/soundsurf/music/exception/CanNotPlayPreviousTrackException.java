package com.api.soundsurf.music.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class CanNotPlayPreviousTrackException extends ApiException {

    private final static String exceptionCode = ExceptionCode.MUSIC.CAN_NOT_PLAY_PREV_MUSIC_EXCEPTION;

    public CanNotPlayPreviousTrackException() {
        super(exceptionCode );
    }
}
