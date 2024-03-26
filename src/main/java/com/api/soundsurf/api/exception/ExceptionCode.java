package com.api.soundsurf.api.exception;

public class ExceptionCode {
    public static class API {
        final public static String UNKNOWN_EXCEPTION = "Unknown Exception";
        final public static String CONNECTION_CREDENTIAL_ERROR = "JDBC url, username, password must not be null";
        final public static String ILLEGAL_ENUM_STRING_EXCEPTION = "Illegal Enum Value";
        final public static String UNAUTHORIZED_TOKEN_EXCEPTION = "Token is not usable";
    }

    public static class USER {
        final public static String NICKNAME_DUPLICATE_ERROR = "Nickname Must Not Be Duplicated";
        final public static String PASSWORD_CONDITION_ERROR = "Password Must Fit With Password Condition";
    }

}