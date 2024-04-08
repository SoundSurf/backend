package com.api.soundsurf.api.exception;

public class ExceptionCode {
    public static class API {
        final public static String UNKNOWN_EXCEPTION = "Unknown Exception";
        final public static String CONNECTION_CREDENTIAL_ERROR = "JDBC url, username, password must not be null";
        final public static String ILLEGAL_ENUM_STRING_EXCEPTION = "Illegal Enum Value";
        final public static String UNAUTHORIZED_TOKEN_EXCEPTION = "Token is not usable";
    }

    public static class USER {
        final public static String NICKNAME_DUPLICATE_ERROR = "닉네임은 중복될수 없습니다.";
        final public static String PASSWORD_CONDITION_ERROR = "비밀번호는 비밀번호 정책과 맞아야 합니다.";
        final public static String USER_NOT_FOUND_ERROR = "사용자를 찾지 못하였습니다.";
        final public static String PASSWORD_NOT_MATCH_ERROR = "비밀번호가 일치 하지 않습니다.";
    }

    public static class SESSION_TOKEN {
        final public static String SESSION_TOKEN_NOT_FOUND_ERROR = "Session Token Not Found";
    }
}