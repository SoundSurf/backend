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
        final public static String NICKNAME_LENGTH_ERROR = "닉네임은 20자 미만으로 쓸 수 있어요.";
        final public static String PASSWORD_CONDITION_ERROR = "Password Must Fit With Password Condition";
        final public static String USER_NOT_FOUND_ERROR = "User Not Found";
        final public static String PASSWORD_NOT_MATCH_ERROR = "Password Not Match";
        final public static String USER_CAR_NOT_MATCH_ERROR = "User Car Not Found";
    }

    public static class USERGENRE {
        final public static String USERGENRE_ALEADY_EXISTS_ERROR = "UserGenre already exists";
    }

    public static class SESSION_TOKEN {
        final public static String SESSION_TOKEN_NOT_FOUND_ERROR = "Session Token Not Found";
    }

    public static class QR {
        final public static String CANNOT_CREATE_QR_ERROR = "Cannot Create QR Code";
        final public static String QR_ALREADY_EXIST_ERROR = "QR Code Already Exist";
        final public static String QR_NOT_FOUND_ERROR = "QR Code Not Found";
    }
}