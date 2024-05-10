package com.api.soundsurf.iam.domain.spotify;

import com.api.soundsurf.iam.dto.MusicDto;
import com.api.soundsurf.iam.exception.SpotifyGenreException;
import com.api.soundsurf.iam.exception.SpotifySearchException;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    // TODO : Search 로직 수정 및 DTO 변경

    private final SpotifyApi spotifyApi;

    public MusicDto.Genre.Response getGenres() {
        try {
            final var genres = spotifyApi.getAvailableGenreSeeds().build().execute();
            return new MusicDto.Genre.Response(Arrays.asList(genres));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyGenreException(e.getMessage());
        }
    }

    public Track[] searchTracks(String title) {
        try {
            final var searchItemRequest = spotifyApi.searchTracks(title)
                    .market(CountryCode.KR)
                    .limit(3)
                    .build();
            return searchItemRequest.execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

    public Artist[] searchArtist(String title) {
        try {
            final var searchItemRequest = spotifyApi.searchArtists(title)
                    .market(CountryCode.KR)
                    .limit(3)
                    .build();
            return searchItemRequest.execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

    public AlbumSimplified[] searchAlbum(String title) {
        try {
            final var searchItemRequest = spotifyApi.searchAlbums(title)
                    .market(CountryCode.KR)
                    .build();
            return searchItemRequest.execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

}
