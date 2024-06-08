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
import java.util.ArrayList;
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

    public MusicDto.NowPlaying.Response getAlbumInfo(final String albumId) {
        try {
            final var album = api.getAlbum(albumId).build().execute();
            final var artist = Arrays.stream(album.getArtists()).map(ArtistSimplified::getName).toArray(String[]::new);
            final var title = Utils.searchAbleString(album.getName());
            final var crawled = crawler.getAlbumGenresRating(title, artist);
            return new MusicDto.NowPlaying.Response(new MusicDto.AlbumFullInfo.Info(album, crawled), Arrays.stream(getRelatedSongs(albumId, album.getArtists()[0].getId())).toList());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }

    private Track[] getTracks(String joinedGenres) {
        try {
            Track[] tracks = null;
            long validPreviewCount = 0;

            while (validPreviewCount < 2) {
                tracks = api.getRecommendations()
                        .seed_genres(joinedGenres)
                        .market(CountryCode.KR)
                        .limit(3)
                        .build()
                        .execute()
                        .getTracks();

                if (tracks == null) {
                    continue;
                }

                validPreviewCount = Arrays.stream(tracks)
                        .filter(track -> track.getPreviewUrl() != null)
                        .count();
            }

            return Arrays.stream(tracks)
                    .filter(track -> track.getPreviewUrl() != null)
                    .toArray(Track[]::new);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }

    private MusicDto.Common.SongSimpleInfo[] getRelatedSongs(String trackId, String artistId) {
        final var tracks = getTracks(trackId, artistId);

        return Arrays.stream(tracks)
                .map(MusicDto.Common.SongSimpleInfo::new)
                .toArray(MusicDto.Common.SongSimpleInfo[]::new);
    }

    private Track[] getTracks(String trackId, String artistId) {
        try {
            List<Track> validTracks = new ArrayList<>();
            int limit = 50;

            while (true) {
                Track[] tracks = api.getRecommendations()
                        .seed_tracks(trackId)
                        .seed_artists(artistId)
                        .market(CountryCode.KR)
                        .limit(limit)
                        .build()
                        .execute()
                        .getTracks();

                if (tracks == null) {
                    continue;
                }

                List<Track> filteredTracks = Arrays.stream(tracks)
                        .filter(track -> track.getPreviewUrl() != null)
                        .toList();

                validTracks.addAll(filteredTracks);

                if (validTracks.size() >= 12) {
                    break;
                }

                if (filteredTracks.size() < limit) {
                    break;
                }
            }

            return validTracks.subList(0, 12).toArray(new Track[0]);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }
}
