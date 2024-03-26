package com.api.soundsurf.iam.controller;

import com.api.soundsurf.RestClientFactory;
import com.api.soundsurf.api.BaseTest;
import com.api.soundsurf.iam.exception.PasswordConditionException;
import com.api.soundsurf.iam.exception.NicknameDuplicateException;
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

    @Description("유저 생성 테스트")
    @TestFactory
    Collection<DynamicNode> signUp() {
        final var userUuids = new ArrayList<String>();

        return group(

                UserControllerTest.create_user("user1", "1234QWER!", "asdfa@gmail.com", userUuids),

                single("같은 아이디로 유저 생성이 안되어야 한다. ", () -> {
                    UserControllerTest.create_user_error("user1", "1234QWER!", "asdfa@gmail.com", NicknameDuplicateException.class);
                }),

                single("비밀번호에는 숫자가 포함되어야 한다.", () -> {
                    UserControllerTest.create_user_error("user2", "qwerQWER!", "asdfa@gmail.com", PasswordConditionException.class);
                }),

                single("비밀번호는 8자리 이상이어야 한다.", () -> {
                    UserControllerTest.create_user_error("user2", "12er!", "asdfa@gmail.com", MethodArgumentNotValidException.class);
                }),

                single("비밀번호에는 영어가 포함되어야 한다.", () -> {
                    UserControllerTest.create_user_error("user2", "12341234!", "asdfa@gmail.com", PasswordConditionException.class);
                }),

                single("비밀번호에는 특수기호가 포함되어야 한다.", () -> {
                    UserControllerTest.create_user_error("user2", "1234QWERQWER", "asdfa@gmail.com", PasswordConditionException.class);
                })
        );
    }

    @Description("유저 로그인 테스트")
    @TestFactory
    Collection<DynamicNode> signIn() {
        final var userTokens = new ArrayList<String>();

        return group();
    }


    public static DynamicNode create_user(final String username, final String password, final String email, final ArrayList<String> userUuids) {
        return single("정상적으로 유저 생성", () -> {
            final var createRequest = new JSONObject();

            createRequest.put("username", username);
            createRequest.put("password", password);
            createRequest.put("email", email);

            final var actual = RestClientFactory.post(CREATE_URL, createRequest);

            org.assertj.core.api.Assertions.assertThat(actual.get("uuid")).isNotNull().isNotEqualTo("");

            userUuids.add(actual.getString("uuid"));
        });
    }

    private static void create_user_error(final String username, final String password, final String email, final Class errorClass) throws JSONException {
        final var createRequest = new JSONObject();

        createRequest.put("username", username);
        createRequest.put("password", password);
        createRequest.put("email", email);

        RestClientFactory.postAssertFail(CREATE_URL, createRequest, errorClass);
    }

}