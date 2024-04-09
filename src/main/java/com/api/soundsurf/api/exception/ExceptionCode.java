package com.api.soundsurf.api.exception;

public class ExceptionCode {
    public static class API {
        final public static String UNKNOWN_EXCEPTION = "Unknown Exception";
        final public static String CONNECTION_CREDENTIAL_ERROR = "JDBC url, username, password must not be null";
        final public static String ILLEGAL_ENUM_STRING_EXCEPTION = "Illegal Enum Value";
        final public static String UNAUTHORIZED_TOKEN_EXCEPTION = "Token is not usable";
    }

    public static class USER {
        final public static String NICKNAME_DUPLICATE_ERROR = "중복된 닉네임입니다.";
        final public static String NICKNAME_LENGTH_ERROR = "닉네임은 20자 미만으로 쓸 수 있어요.";
        final public static String PASSWORD_CONDITION_ERROR = "Password Must Fit With Password Condition";
        final public static String USER_NOT_FOUND_ERROR = "User Not Found";
        final public static String PASSWORD_NOT_MATCH_ERROR = "Password Not Match";
        final public static String ILLEGAL_CAR_ARGUMENT_EXCEPTION = "User does not have the specified car";
        final public static String USER_CAR_NOT_MATCH_ERROR = "User Car Not Found";
    }

    public static class USER_GENRE {
        final public static String USERGENRE_ALEADY_EXISTS_ERROR = "이미 선택한 장르입니다.";
        final public static String USERGENRE_COUNT_LIMIT_ERROR = "장르는 최대 3가지 선택 가능합니다.";
    }

    public static class SESSION_TOKEN {
        final public static String SESSION_TOKEN_NOT_FOUND_ERROR = "Session Token Not Found";
    }

    public static class QR {
        final public static String CANNOT_CREATE_QR_ERROR = "Cannot Create QR Code";
        final public static String QR_ALREADY_EXIST_ERROR = "QR Code Already Exist";
        final public static String QR_NOT_FOUND_ERROR = "QR Code Not Found";
    }

    public static class GENRE {
        final public static String GENRE_NOT_FOUND_EXCEPTION = "Genre Not Found";
    }

    public static class USER_PROFILE {
        final public static String USER_PROFILE_NOT_FOUND_EXCEPTION = "User Profile Not Found";
    }
}