package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.exception.SpotifyNowPlayingException;
import com.api.soundsurf.music.exception.SpotifyRecommendationException;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriveService {

    private final SpotifyApi api;
    private final CrawlerService crawler;

    public Track[] recommendation(final MusicDto.Recommendation.Request request) {
        var genres = request.getGenres();
        try {
            if (genres.isEmpty()) {
                genres = GenreType.getRandomGenres(5);
            }

            final var genreStrings = genres.stream().map(GenreType::getValue).collect(Collectors.toList());
            final var joinedGenres = String.join(",", genreStrings);

            return api.getRecommendations()
                    .seed_genres(joinedGenres)
                    .market(CountryCode.KR)
                    .limit(request.getLimit())
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
            final var artist = album.getArtists()[0].getName().toLowerCase().replace(' ', '-');
            final var title = album.getName().toLowerCase().replace(' ', '-');
            final var crawled = crawler.getAlbumGenresRating(title, artist);
            return new MusicDto.NowPlaying.Response(new MusicDto.AlbumFullInfo.Info(album, crawled));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }


}
