package com.api.soundsurf.music.dto;

import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.constant.SearchType;
import com.api.soundsurf.music.domain.spotify.Utils;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;
import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.Arrays;
import java.util.List;

public class MusicDto {

    public static class Common {
        @Schema(name = "MusicDto.Common.Song")
        public record Song(
                String id,
                String name,
                String previewUrl,
                String spotifyUrl,
                int durationMs,
                AlbumSimpleInfo.Info album,
                List<ArtistSimpleInfo.Musician> artists
        ) {

            public Song(UserRecommendationMusic now) {
                this(
                        now.getTrackId(),
                        now.getTrackName(),
                        now.getTrackPreviewUrl(),
                        now.getTrackSpotifyUrl(),
                        now.getTrackDurationMs(),
                        Utils.convertJsonStringToAlbumDto(now.getAlbumMetadata()),
                        Utils.convertJsonStringToMusicianDtoList(now.getArtistsMetadata()
                        ));
            }


            public Song(Track track) {
                this(
                        track.getId(),
                        track.getName(),
                        track.getPreviewUrl(),
                        track.getExternalUrls().getExternalUrls().get("spotify"),
                        track.getDurationMs(),
                        new AlbumSimpleInfo.Info(track.getAlbum(), null, null),
                        Arrays.stream(track.getArtists()).map(ArtistSimpleInfo.Musician::new).toList()
                );
            }

            public Song(TrackSimplified track) {
                this(
                        track.getId(),
                        track.getName(),
                        track.getPreviewUrl(),
                        track.getExternalUrls().getExternalUrls().get("spotify"),
                        track.getDurationMs(),
                        null,
                        Arrays.stream(track.getArtists()).map(ArtistSimpleInfo.Musician::new).toList()
                );
            }
        }

    }

    public static class AlbumSimpleInfo {
        @Schema(name = "MusicDto.AlbumSimpleInfo.Info")
        public record Info(
                String albumName,
                String id,
                String releaseDate,
                String spotifyUrl,
                String genres,
                String rating,
                List<ArtistSimpleInfo.Musician> artists,
                List<String> images) {
            public Info(Album album, String genres, String rating) {
                this(
                        album.getName(),
                        album.getId(),
                        album.getReleaseDate(),
                        album.getExternalUrls().getExternalUrls().get("spotify"),
                        genres,
                        rating,
                        Arrays.stream(album.getArtists()).map(ArtistSimpleInfo.Musician::new).toList(),
                        Arrays.stream(album.getImages()).map(Image::getUrl).toList());
            }

            public Info(AlbumSimplified album, String genres, String rating) {
                this(
                        album.getName(),
                        album.getId(),
                        album.getReleaseDate(),
                        album.getExternalUrls().getExternalUrls().get("spotify"),
                        genres,
                        rating,
                        Arrays.stream(album.getArtists()).map(ArtistSimpleInfo.Musician::new).toList(),
                        Arrays.stream(album.getImages()).map(Image::getUrl).toList());
            }

            public Info(AlbumSimplified albumSimplified) {
                this(
                        albumSimplified.getName(),
                        albumSimplified.getId(),
                        albumSimplified.getReleaseDate(),
                        albumSimplified.getExternalUrls().getExternalUrls().get("spotify"),
                        null,
                        null,
                        Arrays.stream(albumSimplified.getArtists()).map(ArtistSimpleInfo.Musician::new).toList(),
                        Arrays.stream(albumSimplified.getImages()).map(Image::getUrl).toList());
            }
        }
    }

    public static class AlbumFullInfo {
        @Schema(name = "MusicDto.AlbumFullInfo.Info")
        public record Info(
                AlbumSimpleInfo.Info albumSimple,
                List<Common.Song> songs) {
            public Info(Album album, String[] crawled) {
                this(new AlbumSimpleInfo.Info(album, crawled[0], crawled[1]),
                        Arrays.stream(album.getTracks().getItems()).map(Common.Song::new).toList());
            }
        }
    }

    public static class ArtistSimpleInfo {

        @Schema(name = "MusicDto.ArtistSimpleInfo.Musician")
        public record Musician(
                String artistName,
                String id,
                String spotifyUrl
        ) {
            public Musician(Artist artist) {
                this(
                        artist.getName(),
                        artist.getId(),
                        artist.getExternalUrls().getExternalUrls().get("spotify")
                );
            }

            public Musician(ArtistSimplified artist) {
                this(
                        artist.getName(),
                        artist.getId(),
                        artist.getExternalUrls().getExternalUrls().get("spotify")
                );
            }

            public Musician(final JSONObject nowArtist) {
                this(
                        nowArtist.getString("artistName"),
                        nowArtist.getString("id"),
                        nowArtist.getString("spotifyUrl")
                );
            }
        }
    }

    public interface SearchResult {
    }

    public static class Search {
        @Getter
        @Schema(name = "MusicDto.Search.Request")
        @AllArgsConstructor
        public static class Request {
            @NotNull
            private String title;

            @NotNull
            private int limit;

            @NotNull
            private int offset;

            @NotNull
            private SearchType type;
        }


        public static class Response {
            @Getter
            @Schema(name = "MusicDto.Search.Response")
            @AllArgsConstructor
            public static class Album implements SearchResult {
                private List<AlbumSimpleInfo.Info> albums;
            }

            @Getter
            @Schema(name = "MusicDto.Search.Response")
            @AllArgsConstructor
            public static class Artist implements SearchResult {
                private List<ArtistSimpleInfo.Musician> artists;
            }

            @Getter
            @Schema(name = "MusicDto.Search.Response")
            @AllArgsConstructor
            public static class Track implements SearchResult {
                private List<Common.Song> tracks;
            }
        }
    }

    public static class Genre {
        @Getter
        @Schema(name = "MusicDto.Genre.Response")
        @AllArgsConstructor
        public static class Response {
            private List<String> genres;
        }
    }

    public static class Recommendation {
        @Getter
        @Schema(name = "MusicDto.Recommendation.Request")
        @AllArgsConstructor
        public static class Request {
            @NotNull
            private List<GenreType> genres;
        }
    }

    public static class NowPlaying {
        @Getter
        @Schema(name = "MusicDto.NowPlaying.Request")
        @AllArgsConstructor
        public static class Request {
            @NotNull
            private String id;
        }

        @Getter
        @Schema(name = "MusicDto.NowPlaying.Response")
        @AllArgsConstructor
        public static class Response {
            private AlbumFullInfo.Info album;
        }
    }
}
