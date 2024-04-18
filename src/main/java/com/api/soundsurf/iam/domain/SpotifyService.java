package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.MusicDto;
import com.api.soundsurf.iam.exception.SpotifyGenreException;
import com.api.soundsurf.iam.exception.SpotifyRecommendationException;
import com.api.soundsurf.iam.exception.SpotifySearchException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApi spotifyApi;

    public MusicDto.Common.Response search(final MusicDto.Search.Request request) {
        final var searchItemRequest = spotifyApi.searchTracks(request.getTitle()).build();

        try {
            final var searchResult = searchItemRequest.execute().getItems();
            return new MusicDto.Common.Response(convertToTrackDtoList(searchResult));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

    public MusicDto.Genre.Response getGenres() {
        try {
            final var genres = spotifyApi.getAvailableGenreSeeds().build().execute();
            return new MusicDto.Genre.Response(Arrays.asList(genres));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyGenreException(e.getMessage());
        }
    }

    public MusicDto.Common.Response recommendation(final MusicDto.Recommendation.Request request) {
        try {
            final var recommendations = spotifyApi.getRecommendations()
                    .seed_genres(request.getGenre())
                    .build()
                    .execute()
                    .getTracks();

            return new MusicDto.Common.Response(convertToTrackDtoList(recommendations));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }

    private List<MusicDto.Common.Song> convertToTrackDtoList(final Track[] tracks) {
        return Stream.of(tracks)
                .filter(track -> track.getPreviewUrl() != null)
                .map(MusicDto.Common.Song::new)
                .toList();
    }

    private List<MusicDto.Common.Song> convertToTrackDtoList(final TrackSimplified[] tracks) {
        return Stream.of(tracks)
                .filter(track -> track.getPreviewUrl() != null)
                .map(MusicDto.Common.Song::new)
                .toList();
    }
}
