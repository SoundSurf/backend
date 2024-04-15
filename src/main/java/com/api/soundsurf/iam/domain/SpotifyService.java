package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.MusicDto;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Track;
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

    public List<MusicDto.Search.Response> search(MusicDto.Search.Request request) throws IOException, ParseException, SpotifyWebApiException {

        final var searchItemRequest = spotifyApi.searchTracks(request.getTitle()).build();

        final var searchResult = searchItemRequest.execute().getItems();

        return convertToTrackDtoList(searchResult);
    }

    public MusicDto.Genre.Response getGenres() throws IOException, ParseException, SpotifyWebApiException {
        final var genres = spotifyApi.getAvailableGenreSeeds().build().execute();
        return new MusicDto.Genre.Response(List.of(genres));
    }

    private List<MusicDto.Search.Response> convertToTrackDtoList(Track[] tracks) {
        List<MusicDto.Search.Response> trackDtos = new ArrayList<>();

        for (Track track : tracks) {
            List<String> images = Stream.of(track.getAlbum().getImages())
                    .map(Image::getUrl)
                    .collect(Collectors.toList());

            List<String> artists = Stream.of(track.getArtists())
                    .map(ArtistSimplified::getName)
                    .collect(Collectors.toList());

            trackDtos.add(new MusicDto.Search.Response(
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
}
