package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;

public class ProjectNotFoundException extends ApiException {
        private final static String exceptionCode = ExceptionCode.PROJECT.PROJECT_NOT_FOUND;

        public ProjectNotFoundException(Long id) {
            super(exceptionCode + " : " + id);
        }
}
