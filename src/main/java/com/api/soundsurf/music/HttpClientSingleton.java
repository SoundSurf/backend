package com.api.soundsurf.music;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class HttpClientSingleton {
    private static CloseableHttpClient httpClient = null;


    private HttpClientSingleton() {
    }

    public static CloseableHttpClient getClient() {
        if (httpClient == null) {
            httpClient = createHttpClient();
        }
        return httpClient;
    }

    public static CloseableHttpClient createHttpClient() {
        if (httpClient != null) {
            return httpClient;
        }
        return HttpClients.createDefault();
    }

    public static void closeHttpClient() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                //do nothing
            }
        }
    }
}
