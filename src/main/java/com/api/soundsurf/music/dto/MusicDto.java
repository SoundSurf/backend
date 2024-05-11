package com.api.soundsurf.music.dto;

import com.api.soundsurf.music.entity.GenreType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
                        new AlbumSimpleInfo.Info(track.getAlbum(), null, null),
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
        }
    }

    public static class Search {
        @Getter
        @Setter
        @Schema(name = "MusicDto.Search.Request")
        public static class Request {
            private String title;

            private int limit;

            private int offset;
        }


        public static class Response {
            @Getter
            @Schema(name = "MusicDto.Search.Response")
            @AllArgsConstructor
            public static class Album {
                private List<AlbumSimpleInfo.Info> albums;
            }

            @Getter
            @Schema(name = "MusicDto.Search.Response")
            @AllArgsConstructor
            public static class Artist {
                private List<ArtistSimpleInfo.Musician> artists;
            }

            @Getter
            @Schema(name = "MusicDto.Search.Response")
            @AllArgsConstructor
            public static class Track {
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
