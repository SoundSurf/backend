package com.api.soundsurf.iam.domain.spotify;

import com.api.soundsurf.iam.dto.MusicDto;
import com.api.soundsurf.iam.exception.SpotifyNowPlayingException;
import com.api.soundsurf.iam.exception.SpotifyRecommendationException;
import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.entity.GenreType;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.api.soundsurf.iam.domain.spotify.Utils.convertToTrackDtoList;

@Service
@RequiredArgsConstructor
public class DriveService {

    private final SpotifyApi api;
    private final CrawlerService crawler;

    public MusicDto.Common.Response recommendation(final MusicDto.Recommendation.Request request) {
        var genres = request.getGenres();
        try {
            if (genres.isEmpty()) {
                genres = GenreType.getRandomGenres(5);
            }

            List<String> genreStrings = genres.stream().map(GenreType::getValue).collect(Collectors.toList());
            final String joinedGenres = String.join(",", genreStrings);

            final var recommendations = api.getRecommendations()
                    .seed_genres(joinedGenres)
                    .market(CountryCode.KR)
                    .limit(request.getLimit())
                    .build()
                    .execute()
                    .getTracks();

            return new MusicDto.Common.Response(convertToTrackDtoList(recommendations));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }

    public MusicDto.NowPlaying.Response getNowPlaying(final String albumId) {
        // TODO : 크롤러 사용하여 RYM에서 장르, 평점 가져오기
        try {
            final Album album = api.getAlbum(albumId).build().execute();
            return new MusicDto.NowPlaying.Response(new MusicDto.AlbumFullInfo.Info(album));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }
    

}
