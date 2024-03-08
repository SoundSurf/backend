package com.api.soundsurf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import static com.api.soundsurf.api.session.UserTokenHeader.HEADER_KEY;

public class RestClientFactory {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RestClientFactory.class);
    private static final String BASE_URL = "http://localhost:8080";

    public static JSONObject get(final String uri, final MultiValueMap<String, String> params) {
        final var client = client();
        final var response = client.get().uri(uriBuilder -> uriBuilder.path(uri).queryParams(params).build()).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.GET.name());
            return rawResponse.bodyToMono(Object.class);
        }).block();
        return getJsonObject(response);
    }

    public static JSONObject get(final String uri, final MultiValueMap<String, String> params, final String userToken) {
        final var client = client();
        final var response = client.get().uri(uriBuilder -> uriBuilder.path(uri).queryParams(params).build())
                .header(HEADER_KEY, userToken)
                .exchangeToMono(rawResponse -> {
                    handleException(rawResponse, uri, HttpMethod.GET.name());
                    return rawResponse.bodyToMono(Object.class);
                }).block();
        return getJsonObject(response);
    }

    public static void getAssertFail(final String uri, final MultiValueMap<String, String> params, final Class expectExceptionClass) {
        final var client = client();
        final var response = client.get().uri(uriBuilder -> uriBuilder.path(uri).queryParams(params).build())
                .exchangeToMono(rawResponse -> rawResponse.bodyToMono(Object.class)).block();

        final var jsonObject = getJsonObject(response);
        handleException(jsonObject, expectExceptionClass);
    }

    public static JSONObject post(final String uri, final JSONObject body) {
        final var client = client();

        final var response = client.post().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body.toString())).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.POST.name());
            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static void postAssertFail(final String uri, final JSONObject body, final Class expectExceptionClass) {
        final var client = client();

        final var response = client.post().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body.toString()))
                .exchangeToMono(rawResponse -> rawResponse.bodyToMono(Object.class)).block();

        final var jsonObject = getJsonObject(response);
        handleException(jsonObject, expectExceptionClass);
    }

    public static JSONObject put(final String uri, final JSONObject body, final String userToken) {
        final var client = client();

        final var response = client.put().uri(uri).contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_KEY, userToken)
                .body(BodyInserters.fromValue(body.toString())).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.PUT.name());

            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static JSONObject put(final String uri, final JSONObject body) {
        final var client = client();

        final var response = client.put().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body.toString())).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.PUT.name());

            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static void putAssertFail(final String uri, final JSONObject body, final Class expectExceptionClass) {
        final var client = client();

        final var response = client.put().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body.toString()))
                .exchangeToMono(rawResponse -> rawResponse.bodyToMono(Object.class)).block();

        final var jsonObject = getJsonObject(response);
        handleException(jsonObject, expectExceptionClass);
    }

    public static JSONObject patch(final String uri, final JSONObject body) {
        final var client = client();

        final var response = client.patch().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body.toString())).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.PUT.name());

            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static JSONObject patch(final String uri, final JSONObject body, final String userToken) {
        final var client = client();

        final var response = client.patch().uri(uri).contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_KEY, userToken)
                .body(BodyInserters.fromValue(body.toString())).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.PUT.name());

            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static void patchAssertFail(final String uri, final JSONObject body, final Class expectExceptionClass) {
        final var client = client();

        final var response = client.patch().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body.toString()))
                .exchangeToMono(rawResponse -> rawResponse.bodyToMono(Object.class)).block();

        final var jsonObject = getJsonObject(response);
        handleException(jsonObject, expectExceptionClass);
    }

    public static JSONObject delete(final String uri) {
        final var client = client();

        final var response = client.delete().uri(uri).exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.DELETE.name());

            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static JSONObject delete(final String uri, final String userToken) {
        final var client = client();

        final var response = client.delete().uri(uri)
                .header(HEADER_KEY, userToken)
                .exchangeToMono(rawResponse -> {
            handleException(rawResponse, uri, HttpMethod.DELETE.name());

            return rawResponse.bodyToMono(Object.class);
        }).block();

        return getJsonObject(response);
    }

    public static void deleteAssertFail(final String uri, final Class expectExceptionClass) {
        final var client = client();

        final var response = client.delete().uri(uri)
                .exchangeToMono(rawResponse -> rawResponse.bodyToMono(Object.class)).block();

        final var jsonObject = getJsonObject(response);
        handleException(jsonObject, expectExceptionClass);
    }

    private static JSONObject getJsonObject(final Object response) {
        if (response == null) {
            return null;
        }

        try {
            final var mapper = new ObjectMapper();
            final var jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);

            System.out.println(jsonInString);
            return new JSONObject(jsonInString);

        } catch (JsonProcessingException e) {
            Assertions.fail(e.getMessage() + " (JsonProcessingException)");
            return null;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static WebClient client() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private static void handleException(JSONObject jsonObject, final Class expectExceptionClass) {
        try {
            if (jsonObject.get("exception").equals(expectExceptionClass.getSimpleName())) {
                Assertions.assertTrue(true);

                return;
            }

        } catch (JSONException | NullPointerException e) {
            Assertions.fail(e.getMessage() + " (JsonProcessingException)");

            return;
        }

        Assertions.fail();
    }

    private static void handleException(final ClientResponse rawResponse, final String uri, final String httpMethod) {
        if (rawResponse.statusCode().is4xxClientError()) {
            Assertions.fail(httpMethod + " " + BASE_URL + uri + " failed (api server error)");
        } else if (rawResponse.statusCode().is5xxServerError()) {
            Assertions.fail(httpMethod + " " + BASE_URL + uri + " " + rawResponse.statusCode().value());
        }
    }
}
