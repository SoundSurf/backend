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

    public Track[] recommendation(final List<Integer> genreIndices) {
        List<GenreType> genres;

        if (genreIndices == null || genreIndices.isEmpty()) {
            genres = GenreType.getRandomGenres(5);
        } else {
            genres = genreIndices.stream()
                    .map(GenreType::getByIndex)
                    .collect(Collectors.toList());
        }

        final var genreStrings = genres.stream()
                .map(GenreType::getValue)
                .collect(Collectors.toList());
        final var joinedGenres = String.join(",", genreStrings);

        return getTracksByGenres(joinedGenres);
    }

    public MusicDto.Common.Song getTrackInfo(final String id) {
        try {
            final var track = api.getTrack(id).build().execute();

            return new MusicDto.Common.Song(track);
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }

    public String[] getGenre(final  String artistId) {
        try {
            final var artist = api.getArtist(artistId).build().execute();
            return artist.getGenres();

        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }


    public MusicDto.NowPlaying.Response getAlbumInfo(final String albumId) {
        try {
            final var album = api.getAlbum(albumId).build().execute();
            final var artist = api.getArtist(album.getArtists()[0].getId()).build().execute();
            final var title = Utils.searchAbleString(album.getName());
            final var genres = artist.getGenres();
            return new MusicDto.NowPlaying.Response(new MusicDto.AlbumFullInfo.Info(album, genres), Arrays.stream(getRelatedSongs(albumId, album.getArtists()[0].getId())).toList());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyNowPlayingException(e.getMessage());
        }
    }

    private MusicDto.Common.SongSimpleInfo[] getRelatedSongs(String trackId, String artistId) {
        final var tracks = getTracksByTrackAndArtist(trackId, artistId);

        return Arrays.stream(tracks)
                .map(MusicDto.Common.SongSimpleInfo::new)
                .toArray(MusicDto.Common.SongSimpleInfo[]::new);
    }

    private Track[] getTracks(String seed, boolean isGenre, String secondarySeed) {
        try {
            List<Track> validTracks = new ArrayList<>();
            int limit = 50;
            int targetSize = isGenre ? 3 : 12;

            while (true) {
                var recommendationRequest = api.getRecommendations()
                        .market(CountryCode.KR)
                        .limit(limit);

                if (isGenre) {
                    recommendationRequest.seed_genres(seed);
                } else {
                    recommendationRequest.seed_tracks(seed).seed_artists(secondarySeed);
                }

                Track[] tracks = recommendationRequest.build()
                        .execute()
                        .getTracks();

                if (tracks == null) {
                    continue;
                }

                List<Track> filteredTracks = Arrays.stream(tracks)
                        .filter(track -> track.getPreviewUrl() != null)
                        .toList();

                validTracks.addAll(filteredTracks);

                if (validTracks.size() >= targetSize) {
                    break;
                }

                if (filteredTracks.size() < limit) {
                    break;
                }
            }

            return validTracks.subList(0, targetSize).toArray(new Track[0]);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRecommendationException(e.getMessage());
        }
    }

    private Track[] getTracksByGenres(String joinedGenres) {
        return getTracks(joinedGenres, true, null);
    }

    private Track[] getTracksByTrackAndArtist(String trackId, String artistId) {
        return getTracks(trackId, false, artistId);
    }

}
