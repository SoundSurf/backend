package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.exception.CannotCreateSpotifyTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@RequiredArgsConstructor
@Slf4j
@Component
public class SpotifyTokenProvider {

    private final SpotifyApi spotifyApi;

    public void getAccessToken() {
        final var clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            final var clientCredentials = clientCredentialsRequest.execute();
            final var token = clientCredentials.getAccessToken();
            spotifyApi.setAccessToken(token);
            log.info("Access token: " + token);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw new CannotCreateSpotifyTokenException(e.getMessage());
        }
    }
}