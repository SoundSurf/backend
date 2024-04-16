package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.MusicDto;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApi spotifyApi;

    public MusicDto.Common.Response search(MusicDto.Search.Request request) throws IOException, ParseException, SpotifyWebApiException {

        final var searchItemRequest = spotifyApi.searchTracks(request.getTitle()).build();

        final var searchResult = searchItemRequest.execute().getItems();

        return new MusicDto.Common.Response(convertToTrackDtoList(searchResult));
    }

    public MusicDto.Genre.Response getGenres() throws IOException, ParseException, SpotifyWebApiException {
        final var genres = spotifyApi.getAvailableGenreSeeds().build().execute();
        return new MusicDto.Genre.Response(List.of(genres));
    }

    public MusicDto.Common.Response recommendation(MusicDto.Recommendation.Request request) throws IOException, ParseException, SpotifyWebApiException {
        final var recommendations = spotifyApi.getRecommendations()
                .seed_genres(request.getGenre())
                .build()
                .execute()
                .getTracks();


        return new MusicDto.Common.Response(convertToTrackDtoList(recommendations));
    }

    private List<MusicDto.Common.Track> convertToTrackDtoList(Track[] tracks) {
        List<MusicDto.Common.Track> trackDtos = new ArrayList<>();

        for (Track track : tracks) {
            if (track.getPreviewUrl() == null) continue;

            final var images = Stream.of(track.getAlbum().getImages())
                    .map(Image::getUrl)
                    .collect(Collectors.toList());

            final var artists = Stream.of(track.getArtists())
                    .map(ArtistSimplified::getName)
                    .collect(Collectors.toList());

            trackDtos.add(new MusicDto.Common.Track(
                    track.getAlbum().getName(),
                    artists,
                    images,
                    track.getAlbum().getReleaseDate(),
                    track.getName(),
                    track.getPreviewUrl(),
                    track.getDurationMs()
            ));
        }

        return trackDtos;
    }

    private List<MusicDto.Common.Track> convertToTrackDtoList(TrackSimplified[] tracks) {
        List<MusicDto.Common.Track> trackDtos = new ArrayList<>();

        for (TrackSimplified track : tracks) {
            if (track.getPreviewUrl() == null) continue;

            final var artists = Stream.of(track.getArtists())
                    .map(ArtistSimplified::getName)
                    .collect(Collectors.toUnmodifiableList());

            trackDtos.add(new MusicDto.Common.Track(
                    track.getName(),
                    artists,
                    null,
                    null,
                    track.getName(),
                    track.getPreviewUrl(),
                    track.getDurationMs()
            ));
        }

        return trackDtos;
    }

}
