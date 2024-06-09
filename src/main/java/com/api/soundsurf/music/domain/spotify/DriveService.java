package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.exception.SpotifyNowPlayingException;
import com.api.soundsurf.music.exception.SpotifyRecommendationException;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
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
public class DriveService {

    private final SpotifyApi api;
    private final CrawlerService crawler;

    public Track[] recommendation(final List<GenreType> requestGenres) {
        List<GenreType> genres;

        try {
            if (requestGenres.isEmpty()) {
                genres = GenreType.getRandomGenres(5);
            } else {
                genres = requestGenres;
            }

            final var genreStrings = genres.stream().map(GenreType::getValue).collect(Collectors.toList());
            final var joinedGenres = String.join(",", genreStrings);

            return api.getRecommendations()
                    .seed_genres(joinedGenres)
                    .market(CountryCode.KR)
                    .limit(3)
                    .build()
                    .execute()
                    .getTracks();

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
