package com.api.soundsurf.iam.controller;

import com.api.soundsurf.RestClientFactory;
import com.api.soundsurf.api.BaseTest;
import com.api.soundsurf.iam.exception.PasswordConditionException;
import com.api.soundsurf.iam.exception.NicknameDuplicateException;
import com.api.soundsurf.iam.exception.PasswordNotMatchException;
import jdk.jfr.Description;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Collection;

class UserControllerTest extends BaseTest {
    private final static String CLASS_URL = "/user";
    private final static String CREATE_URL = CLASS_URL + "/create";
    private final static String LOGIN = CLASS_URL + "/login";

    @Description("유저 생성 테스트")
    @TestFactory
    Collection<DynamicNode> signUp() {
        final var userTokens = new ArrayList<String>();

        return group(

                UserControllerTest.create_user("1234QWER!", "asdfa@gmail.com", userTokens),

                single("같은 이메일로 유저 생성이 안되어야 한다. ", () -> {
                    UserControllerTest.create_user_error("1234QWER!", "asdfa@gmail.com", NicknameDuplicateException.class);
                }),

                single("비밀번호에는 숫자가 포함되어야 한다.", () -> {
                    UserControllerTest.create_user_error("qwerQWER!", "asdfa2@gmail.com", PasswordConditionException.class);
                }),

                single("비밀번호는 6자리 이상이어야 한다.", () -> {
                    UserControllerTest.create_user_error("12er!", "asdfa3@gmail.com", MethodArgumentNotValidException.class);
                }),

                single("비밀번호에는 영어가 포함되어야 한다.", () -> {
                    UserControllerTest.create_user_error("12341234!", "asdfa4@gmail.com", PasswordConditionException.class);
                })

        );
    }

    @Description("유저 로그인 테스트")
    @TestFactory
    Collection<DynamicNode> signIn() {
        final var userTokens = new ArrayList<String>();
        final var userEmail = "asdfa@gmail.com";
        final var userPassword = "1234QWER!";

        return group(
                UserControllerTest.create_user(userPassword, userEmail, userTokens),

                UserControllerTest.login(userEmail, userPassword, userTokens),
                UserControllerTest.login_fail(userEmail, "wrongPassword123")
        );
    }

    public static DynamicNode login(final String email, final String password, final ArrayList<String> userTokens) {
        return single("정상적으로 로그인", () -> {
            final var loginRequest = new JSONObject();

            loginRequest.put("password", password);
            loginRequest.put("email", email);

            final var actual = RestClientFactory.post(LOGIN, loginRequest);

            org.assertj.core.api.Assertions.assertThat(actual.get("userToken")).isNotNull().isNotEqualTo(userTokens.get(0));
        });
    }

    private static DynamicNode login_fail(final String email, final String password) {
        return single("틀린 비밀번호를 입력하면  로그인 실패", () -> {
            final var loginRequest = new JSONObject();

            loginRequest.put("password", password);
            loginRequest.put("email", email);

            RestClientFactory.postAssertFail(LOGIN, loginRequest, PasswordNotMatchException.class);
        });
    }


    public static DynamicNode create_user(final String password, final String email, final ArrayList<String> userTokens) {
        return single("정상적으로 유저 생성", () -> {
            final var createRequest = new JSONObject();

            createRequest.put("password", password);
            createRequest.put("email", email);

            final var actual = RestClientFactory.post(CREATE_URL, createRequest);

            org.assertj.core.api.Assertions.assertThat(actual.get("userToken")).isNotNull().isNotEqualTo("");

            userTokens.add(actual.getString("userToken"));
        });
    }

    private static void create_user_error(final String password, final String email, final Class errorClass) throws JSONException {
        final var createRequest = new JSONObject();

        createRequest.put("password", password);
        createRequest.put("email", email);

        RestClientFactory.postAssertFail(CREATE_URL, createRequest, errorClass);
    }

}