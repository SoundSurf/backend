package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.constant.SearchType;
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

    public MusicDto.SearchResult search(final String title,final int limit,final int offset,final SearchType type) {
        try {
            final var result = api.searchItem(title, type.getType())
                    .market(CountryCode.KR)
                    .limit(limit)
                    .offset(offset)
                    .build()
                    .execute();

            return switch (type) {
                case TRACK -> new MusicDto.Search.Response.Track(
                        Arrays.stream(result.getTracks().getItems()).map(MusicDto.Common.Song::new).toList());
                case ARTIST -> new MusicDto.Search.Response.Artist(
                        Arrays.stream(result.getArtists().getItems()).map(MusicDto.ArtistSimpleInfo.Musician::new).toList());
                case ALBUM -> new MusicDto.Search.Response.Album(
                        Arrays.stream(result.getAlbums().getItems()).map(MusicDto.AlbumSimpleInfo.Info::new).toList());
            };
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }


}
