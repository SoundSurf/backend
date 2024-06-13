package com.api.soundsurf.list.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PlaylistDto {
    @AllArgsConstructor

    public static class Get {
        @Schema(name = "PlaylistDto.Get.Response")
        @Getter
        @AllArgsConstructor
        public static class Response {
            private Long id;
            private String name;
            private boolean isComplete;
            private boolean isDeleted;
            private LocalDateTime createdAt;
            private Integer musicCount;
            private java.util.List<Long> genreIds;
            private java.util.List<MusicWithMemo> playlistMusics;
        }

        @Schema(name = "PlaylistDto.Get.MusicWithMemo")
        @Getter
        @AllArgsConstructor
        public static class MusicWithMemo {
            private com.api.soundsurf.music.entity.Music music;
            private String memo;
        }
    }

    public static class List{
        @Schema(name = "PlaylistDto.List.Response")
        @Getter
        @AllArgsConstructor
        public static class Response {
            private java.util.List<PlaylistSummary> completed;
            private java.util.List<PlaylistSummary> unCompleted;
        }

        @Schema(name = "PlaylistDto.List.PlaylistSummary")
        @Getter
        @AllArgsConstructor
        public static class PlaylistSummary {
            private String name;
            private LocalDateTime createdAt;
            private Integer musicCount;
        }
    }

    public static class Create {
        @Schema(name = "PlaylistDto.Create.Request")
        @Getter
        public static class Request {
            @Length(max = 50)
            @NotEmpty
            private String name;
            private java.util.List<Long> genreIds;
        }

        @Schema(name = "PlaylistDto.Create.Response")
        @AllArgsConstructor
        @Getter
        public static class Response {
            private Long id;
        }
    }

    public static class Music {
        @Schema(name = "PlaylistDto.Music.Request")
        @Getter
        public static class Request {
            private String trackId;
            private String title;
            private String artists;
            private String imageUrl;
        }
    }

    public static class Memo {
        @Schema(name = "PlaylistDto.Memo.Request")
        @Getter
        public static class Request {
            private Long musicId;
            private String memo;
        }
    }
}
