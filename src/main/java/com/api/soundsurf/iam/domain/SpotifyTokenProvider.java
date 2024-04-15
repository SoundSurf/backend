package com.api.soundsurf.iam.domain;

import com.wrapper.spotify.SpotifyApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class SpotifyTokenProvider {

    private final SpotifyApi spotifyApi;

    public void getAccessToken() {
        final var clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            final var clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            log.info("Access token: " + clientCredentials.getAccessToken());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}