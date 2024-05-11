package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.exception.SpotifySearchException;
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

    public MusicDto.SearchResult search(MusicDto.Search.Request request) {
        try {
            final var result = api.searchItem(request.getTitle(), request.getType().getType())
                    .market(CountryCode.KR)
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .build()
                    .execute();

            switch (request.getType()) {
                case TRACK:
                    return new MusicDto.Search.Response.Track(
                            Arrays.stream(result.getTracks().getItems()).map(MusicDto.Common.Song::new).toList());
                case ARTIST:
                    return new MusicDto.Search.Response.Artist(
                            Arrays.stream(result.getArtists().getItems()).map(MusicDto.ArtistSimpleInfo.Musician::new).toList());
                case ALBUM:
                    return new MusicDto.Search.Response.Album(
                            Arrays.stream(result.getAlbums().getItems()).map(MusicDto.AlbumSimpleInfo.Info::new).toList());
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
        return null;
    }


}
