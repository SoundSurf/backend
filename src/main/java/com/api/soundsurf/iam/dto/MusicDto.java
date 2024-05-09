package com.api.soundsurf.iam.dto;

import com.api.soundsurf.music.entity.GenreType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;
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

            public Song(Track track) {
                this(
                        track.getId(),
                        track.getName(),
                        track.getPreviewUrl(),
                        track.getExternalUrls().getExternalUrls().get("spotify"),
                        track.getDurationMs(),
                        new AlbumSimpleInfo.Info(track.getAlbum()),
                        Arrays.stream(track.getArtists()).map(ArtistSimpleInfo.Musician::new).toList()
                );
            }
        }

        @Getter
        @RequiredArgsConstructor
        @Schema(name = "MusicDto.Common.Response")
        public static class Response {
            private final List<Song> songs;
        }
    }

    public static class AlbumSimpleInfo {
        public record Info(
                String albumName,
                String id,
                ReleaseDatePrecision releasedDate,
                String spotifyUrl,
                List<ArtistSimpleInfo.Musician> artists,
                List<String> images) {
            public Info(Album album) {
                this(
                        album.getName(),
                        album.getId(),
                        album.getReleaseDatePrecision(),
                        album.getExternalUrls().getExternalUrls().get("spotify"),
                        Arrays.stream(album.getArtists()).map(ArtistSimpleInfo.Musician::new).toList(),
                        Arrays.stream(album.getImages()).map(Image::getUrl).toList());
            }

            public Info(AlbumSimplified album) {
                this(
                        album.getName(),
                        album.getId(),
                        album.getReleaseDatePrecision(),
                        album.getExternalUrls().getExternalUrls().get("spotify"),
                        Arrays.stream(album.getArtists()).map(ArtistSimpleInfo.Musician::new).toList(),
                        Arrays.stream(album.getImages()).map(Image::getUrl).toList());
            }
        }
    }

    public static class AlbumFullInfo {
        public record Info(
                AlbumSimpleInfo.Info albumSimple,
                List<Common.Song> songs) {
            public Info(Album album) {
                this(new AlbumSimpleInfo.Info(album),
                        Arrays.stream(album.getTracks().getItems()).map(Common.Song::new).toList());
            }
        }
    }

    public static class ArtistSimpleInfo {
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
        }

    }

    public static class Search {
        @Getter
        @Schema(name = "MusicDto.Search.Request")
        public static class Request {
            @NotNull
            private String title;
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
        public static class Request {
            private List<GenreType> genres;
            private int limit;
        }
    }

    public static class NowPlaying {
        @Getter
        @Schema(name = "MusicDto.NowPlaying.Request")
        public static class Request {
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
