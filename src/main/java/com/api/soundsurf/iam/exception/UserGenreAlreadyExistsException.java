package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;
import com.api.soundsurf.iam.entity.Genre;
import com.api.soundsurf.iam.entity.User;

public class UserGenreAlreadyExistsException extends ApiException {
    private final static String exceptionCode = ExceptionCode.USERGENRE.USERGENRE_ALEADY_EXISTS_ERROR;
    public UserGenreAlreadyExistsException(final User user, final Genre genre) {
        super(exceptionCode + " : " + user.getNickname() + ", " + genre.getName());
    }
}
