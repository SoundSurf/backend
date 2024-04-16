package com.api.soundsurf.api.exception;

public class ExceptionCode {
    public static class API {
        final public static String UNKNOWN_EXCEPTION = "알 수 없는 오류가 발생했습니다.";
        final public static String CONNECTION_CREDENTIAL_ERROR = "JDBC url, 이메일, 비밀번호를 확인해주세요.";
        final public static String ILLEGAL_ENUM_STRING_EXCEPTION = "잘못된 Enum 문자열입니다.";
        final public static String UNAUTHORIZED_TOKEN_EXCEPTION = "토큰이 유효하지 않습니다.";
    }

    public static class USER {
        final public static String NICKNAME_DUPLICATE_ERROR = "중복된 닉네임입니다.";
        final public static String NICKNAME_LENGTH_ERROR = "닉네임은 20자 미만으로 쓸 수 있어요.";
        final public static String PASSWORD_CONDITION_ERROR = "비밀번호 조건을 확인해주세요.";
        final public static String USER_NOT_FOUND_ERROR = "유저를 찾을 수 없습니다.";
        final public static String PASSWORD_NOT_MATCH_ERROR = "비밀번호가 일치하지 않습니다.";
        final public static String ILLEGAL_CAR_ARGUMENT_EXCEPTION = "잘못된 차량 정보입니다.";
        final public static String USER_CAR_NOT_MATCH_ERROR = "유저와 차량이 일치하지 않습니다.";
    }

    public static class USER_GENRE {
        final public static String USERGENRE_ALEADY_EXISTS_ERROR = "이미 선택한 장르입니다.";
        final public static String USERGENRE_COUNT_LIMIT_ERROR = "장르는 최대 3가지 선택 가능합니다.";
    }

    public static class SESSION_TOKEN {
        final public static String SESSION_TOKEN_NOT_FOUND_ERROR = "토큰을 찾을 수 없습니다.";
    }

    public static class QR {
        final public static String CANNOT_CREATE_QR_ERROR = "QR 코드를 생성할 수 없습니다.";
        final public static String QR_ALREADY_EXIST_ERROR = "QR 코드가 이미 존재합니다.";
        final public static String QR_NOT_FOUND_ERROR = "QR 코드를 찾을 수 없습니다.";
    }

    public static class GENRE {
        final public static String GENRE_NOT_FOUND_EXCEPTION = "장르를 찾을 수 없습니다.";
    }

    public static class USER_PROFILE {
        final public static String USER_PROFILE_NOT_FOUND_EXCEPTION = "유저 프로필을 찾을 수 없습니다.";
    }

    public static class SPOTIFY {
        final public static String SPOTIFY_TOKEN_NOT_CREATED = "토큰을 생성할 수 없습니다.";
        final public static String SPOTIFY_SEARCH_ERROR = "음악 검색 중 오류가 발생했습니다.";
        final public static String SPOTIFY_RECOMMENDATION_ERROR = "음악 추천 중 오류가 발생했습니다.";
        final public static String SPOTIFY_GENRE_ERROR = "장르 검색 중 오류가 발생했습니다.";
    }
}