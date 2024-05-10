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

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SpotifyApi api;

    public MusicDto.Genre.Response getGenres() {
        try {
            final var genres = api.getAvailableGenreSeeds().build().execute();
            return new MusicDto.Genre.Response(Arrays.asList(genres));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyGenreException(e.getMessage());
        }
    }

    public MusicDto.Search.Response.Track searchTracks(MusicDto.Search.Request request) {
        try {
            final var tracks = api.searchTracks(request.getTitle())
                    .market(CountryCode.KR)
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .build()
                    .execute()
                    .getItems();

            return new MusicDto.Search.Response.Track(
                    Arrays.stream(tracks).map(MusicDto.Common.Song::new).toList());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

    public MusicDto.Search.Response.Artist searchArtist(MusicDto.Search.Request request) {
        try {
            final var artists = api.searchArtists(request.getTitle())
                    .market(CountryCode.KR)
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .build()
                    .execute()
                    .getItems();

            return new MusicDto.Search.Response.Artist(
                    Arrays.stream(artists).map(MusicDto.ArtistSimpleInfo.Musician::new).toList());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

    public MusicDto.Search.Response.Album searchAlbum(MusicDto.Search.Request request) {
        try {
            final var albums = api.searchAlbums(request.getTitle())
                    .market(CountryCode.KR)
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .build()
                    .execute()
                    .getItems();

            return new MusicDto.Search.Response.Album(
                    Arrays.stream(albums).map(MusicDto.AlbumSimpleInfo.Info::new).toList());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }

}
