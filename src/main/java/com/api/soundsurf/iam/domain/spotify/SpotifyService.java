package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.MusicDto;
import com.api.soundsurf.iam.exception.SpotifyGenreException;
import com.api.soundsurf.iam.exception.SpotifyRecommendationException;
import com.api.soundsurf.iam.exception.SpotifyRecommendationSeedException;
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
        switch (request.getType()) {
            case ARTIST:

                break;
            case ALBUM:
                break;
            case TRACK:
                return new MusicDto.Common.Response(searchTracks(request.getTitle()));
        }
        // TODO: Search exception 으로 변경
        throw new SpotifyRecommendationSeedException();
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
//            if (request.getGenres().isEmpty() && request.getTrack().isEmpty() && request.getArtist().isEmpty()) {
//                throw new SpotifyRecommendationSeedException();
//            }

            final var joinedGenres = String.join(",", request.getGenres());

            final var recommendations = spotifyApi.getRecommendations()
                    .seed_genres(joinedGenres)
                    .seed_tracks(request.getTrack())
//                    .seed_artists(request.getArtist())
                    .market(CountryCode.KR)
                    .limit(30)
                    .build()
                    .execute()
                    .getTracks();

            return new MusicDto.Common.Response(convertToTrackDtoList(recommendations));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }

    private List<MusicDto.Common.Song> searchTracks(String title) {
        try {
            final var searchItemRequest = spotifyApi.searchTracks(title)
                    .market(CountryCode.KR)
                    .limit(3)
                    .build();
            final var searchResult = searchItemRequest.execute().getItems();
            return convertToTrackDtoList(searchResult);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

    public Track[] searchTracks2(String title) {
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

    public Track[] recommend() {
        try {
            final var recommendations = spotifyApi.getRecommendations()
                    .seed_genres("synth-pop")
                    .market(CountryCode.KR)
                    .limit(10)
                    .build()
                    .execute()
                    .getTracks();
            return recommendations;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
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
