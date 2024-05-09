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

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlerService {

    public String[] getAlbumGenresRating(final String title, final String artist) {
        HttpGet request = new HttpGet("https://rateyourmusic.com/release/single/" + artist + "/" + title + "/");

        return getGenresRating(request);
    }

    public String[] getMusicGenresRating(final String title, final String artist) {
        HttpGet request = new HttpGet("https://rateyourmusic.com/release/single/" + artist + "/" + title + "/");

        return getGenresRating(request);
    }

    private String[] getGenresRating(final HttpGet request) {
        try (CloseableHttpResponse response = HttpClientSingleton.getClient().execute(request)) {
            final var entity = response.getEntity();
            final var html = EntityUtils.toString(entity);
            final var doc = Jsoup.parse(html);

            final var genres = doc.select("span.release_pri_genres").text();
            final var rating = doc.select("span.avg_rating").text();

            return new String[]{genres, rating};
        } catch (IOException | ParseException e) {
            throw new ApiException("RYM 사이트 에러 발생");
        }
    }

}
