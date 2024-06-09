package com.api.soundsurf.music.domain;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.music.HttpClientSingleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlerService {

    private final static String RYM_ALBUM_URL = "http://rateyourmusic.com/release/album/";

    public String[] getAlbumGenresRating(final String title, final String[] artists) {
        String formattedTitle = formatRYM(title);
        String formattedArtist = Arrays.stream(artists)
                .map(this::formatRYM)
                .collect(Collectors.joining("-"));

        HttpGet request = new HttpGet(RYM_ALBUM_URL + formattedArtist + "/" + formattedTitle + "/");

        try {
            return getGenresRating(request);
        } catch (ApiException e) {
            if (artists.length > 1) {
                request = new HttpGet(RYM_ALBUM_URL + "various-artists/" + formattedTitle + "/");
                return getGenresRating(request);
            } else {
                throw new ApiException("앨범 장르 및 평점 정보 가져오기 실패");
            }
        }
    }

    private String[] getGenresRating(final HttpGet request) {
        try (CloseableHttpResponse response = HttpClientSingleton.getClient().execute(request)) {
            final var entity = response.getEntity();
            final var html = EntityUtils.toString(entity);
            final var doc = Jsoup.parse(html);

            var genres = doc.select("span.release_pri_genres").text();
            genres = genres.isEmpty() ? "Unknown" : genres;
            var rating = doc.select("span.avg_rating").text();
            rating = rating.isEmpty() ? "Unknown" : rating;

            return new String[]{genres, rating};
        } catch (IOException | ParseException e) {
            throw new ApiException("RYM 사이트 에러 발생");
        }
    }

    private String formatRYM(String input) {
        if (input == null) {
            return null;
        }
        return input.toLowerCase().replace(" ", "-");
    }
}
