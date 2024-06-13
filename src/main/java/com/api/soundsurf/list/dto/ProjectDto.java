package com.api.soundsurf.list.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class ProjectDto {
    @AllArgsConstructor

    public static class Get {
        @Schema(name = "ProjectDto.Get.Response")
        @Getter
        @AllArgsConstructor
        public static class Response {
            private String name;
            private boolean isComplete;
            private boolean isDeleted;
            private LocalDateTime createdAt;
            private Integer musicCount;
            private java.util.List<Long> genreIds;
            private java.util.List<MusicWithMemo> projectMusics;
        }

        @Schema(name = "ProjectDto.Get.MusicWithMemo")
        @Getter
        @AllArgsConstructor
        public static class MusicWithMemo {
            private com.api.soundsurf.music.entity.Music music;
            private String memo;
        }
    }

    public static class List{
        @Schema(name = "ProjectDto.List.Response")
        @Getter
        @AllArgsConstructor
        public static class Response {
            private java.util.List<ProjectSummary> completed;
            private java.util.List<ProjectSummary> unCompleted;
        }

        @Schema(name = "ProjectDto.List.ProjectSummary")
        @Getter
        @AllArgsConstructor
        public static class ProjectSummary {
            private String name;
            private LocalDateTime createdAt;
            private Integer musicCount;
        }
    }

    public static class Create {
        @Schema(name = "ProjectDto.Create.Request")
        @Getter
        public static class Request {
            @Length(max = 50)
            @NotEmpty
            private String name;
            private java.util.List<Long> genreIds;
        }

        @Schema(name = "ProjectDto.Create.Response")
        @AllArgsConstructor
        @Getter
        public static class Response {
            private Long id;
        }
    }

    public static class Music {
        @Schema(name = "ProjectDto.Music.Request")
        @Getter
        public static class Request {
            private String trackId;
            private String title;
            private String artists;
            private String imageUrl;
        }
    }

    public static class Memo {
        @Schema(name = "ProjectDto.Memo.Request")
        @Getter
        public static class Request {
            private String trackId;
            private String memo;
        }
    }
//
//    public static class Update {
//        @Schema(name = "ProjectDto.Update.Request")
//        @Getter
//        public static class Request {
//            @Length(max = 50)
//            private String name;
//            @Length()
//            private String memo;
//        }
//    }
//
//    public static class SaveEntity {
//        @Schema(name = "ProjectDto.SaveEntity.Request")
//        @Getter
//        public static class Request{
//            private EntityType entityType;
//        }
//    }
}
