package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class MusicDto {

    public static class Common {
        @Getter
        @AllArgsConstructor
        @Schema(name = "MusicDto.Common.Response")
        public static class Response {
            private String albumName;
            private List<String> artistName;
            private List<String> images;
            private String releaseDate;
            private String trackName;
            private String previewUrl;
            private int durationMs;
        }
    }

    public static class Search {
        @Getter
        @Schema(name = "MusicDto.Search.Request")
        public static class Request {
            private String title;

        }


    }

    public static class Genre {
        @Getter
        @AllArgsConstructor
        @Schema(name = "MusicDto.Genre.Response")
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
