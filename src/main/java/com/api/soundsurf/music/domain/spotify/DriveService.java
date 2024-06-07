package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.exception.SpotifyNowPlayingException;
import com.api.soundsurf.music.exception.SpotifyRecommendationException;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriveService {

    private final SpotifyApi api;
    private final CrawlerService crawler;

    public Track[] recommendation(final List<GenreType> requestGenres) {
        List<GenreType> genres;

        if (requestGenres.isEmpty()) {
            genres = GenreType.getRandomGenres(5);
        } else {
            genres = requestGenres;
        }

        final var genreStrings = genres.stream().map(GenreType::getValue).collect(Collectors.toList());
        final var joinedGenres = String.join(",", genreStrings);

        return getTracks(joinedGenres);
    }

    private Track[] getTracks(String joinedGenres) {
        try {

            final var tracks = api.getRecommendations()
                    .seed_genres(joinedGenres)
                    .market(CountryCode.KR)
                    .limit(3)
                    .build()
                    .execute()
                    .getTracks();

            if (tracks == null || Arrays.stream(tracks).allMatch(track -> track.getPreviewUrl() == null)) {
                return getTracks(joinedGenres); // 메서드 자신을 재귀적으로 호출
            }

            return Arrays.stream(tracks)
                    .filter(track -> track.getPreviewUrl() != null)
                    .toArray(Track[]::new);
            
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }

    public MusicDto.NowPlaying.Response getNowPlayingAlbum(final String albumId) {
        try {
            final var album = api.getAlbum(albumId).build().execute();
            final var artist = Arrays.stream(album.getArtists()).map(ArtistSimplified::getName).toArray(String[]::new);
            final var title = Utils.searchAbleString(album.getName());
            final var crawled = crawler.getAlbumGenresRating(title, artist);
            return new MusicDto.NowPlaying.Response(new MusicDto.AlbumFullInfo.Info(album, crawled));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }


}
