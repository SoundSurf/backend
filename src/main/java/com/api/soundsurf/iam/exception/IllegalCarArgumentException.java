package com.api.soundsurf.iam.exception;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.api.exception.ExceptionCode;
import com.api.soundsurf.iam.entity.Car;

public class IllegalCarArgumentException extends ApiException {
    private final static String exceptionCode = ExceptionCode.USER.ILLEGAL_CAR_ARGUMENT_EXCEPTION;

    public IllegalCarArgumentException(final Car userCar, final Car selectedCar) {
        super(exceptionCode + " : " + userCar.getName() + ", " + selectedCar.getName());
    }

    public IllegalCarArgumentException(final Long id) {
        super (exceptionCode + " : " + id);
    }
}
