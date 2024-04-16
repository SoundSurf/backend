package com.api.soundsurf.iam.dto;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class MusicDto {

    public static class Common {
        @Schema(name = "MusicDto.Common.Track")
        public record Song(
                String albumName,
                List<String> artistName,
                List<String> images,
                String releaseDate,
                String trackName,
                String previewUrl,
                int durationMs
        ) {
            public Song(TrackSimplified track) {
                this(track.getName(),
                        Arrays.stream(track.getArtists()).map(ArtistSimplified::getName).toList(),
                        null,
                        null,
                        track.getName(),
                        track.getPreviewUrl(),
                        track.getDurationMs());
            }

            public Song(Track track) {
                this(track.getAlbum().getName(),
                        Arrays.stream(track.getArtists()).map(ArtistSimplified::getName).toList(),
                        Arrays.stream(track.getAlbum().getImages()).map(image -> image.getUrl()).toList(),
                        track.getAlbum().getReleaseDate(),
                        track.getName(),
                        track.getPreviewUrl(),
                        track.getDurationMs());
            }
        }

        @Getter
        @RequiredArgsConstructor
        @Schema(name = "MusicDto.Common.Response")
        public static class Response {
            private final List<Song> songs;
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
            private String genre;
        }
    }
}
