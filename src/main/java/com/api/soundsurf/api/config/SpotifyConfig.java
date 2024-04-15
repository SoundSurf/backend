package com.api.soundsurf.api.config;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyConfig {

    @Bean
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder()
                .setClientId(System.getenv("SPOTIFY_CLIENT_ID"))
                .setClientSecret(System.getenv("SPOTIFY_CLIENT_SECRET"))
                .build();
    }
}
