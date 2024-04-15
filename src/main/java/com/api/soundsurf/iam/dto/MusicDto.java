package com.api.soundsurf.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class MusicDto {

    public static class Search {

        @Getter
        public static class Request {
            private String title;
        }

        @Getter
        @AllArgsConstructor
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

    public static class Genre {
        @Getter
        @AllArgsConstructor
        public static class Response {
            private List<String> genres;
        }
    }
}
