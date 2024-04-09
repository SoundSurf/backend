package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.entity.Genre;

public class UserGenreAlreadyExistsException extends ApiException {
    private final static String exceptionCode = ExceptionCode.USER_GENRE.USERGENRE_ALEADY_EXISTS_ERROR;

    public UserGenreAlreadyExistsException(final User user, final Genre genre) {
        super(exceptionCode + " : " + user.getNickname() + ", " + genre.getName());
    }
}
