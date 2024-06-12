package com.api.soundsurf.list.dto;

import com.api.soundsurf.list.constant.EntityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class ProjectDto {
//    @AllArgsConstructor

//    public static class Project {
//        private String name;
//        private String memo;
//        private java.util.List<Long> genreIds;
//
//        public Project(final com.api.soundsurf.list.entity.Project project, final java.util.List<Long> gerneIds){
//            this.name = project.getName();
//            this.memo = project.getMemo();
//            this.genreIds = gerneIds;
//        }
//    }
//
//    public static class List{
//
//        @Schema(name = "ProjectDto.List.Response")
//        @Getter
//        @AllArgsConstructor
//        public static class Response {
//            private java.util.List<Project> completed;
//            private java.util.List<Project> unCompleted;
//        }
//    }
//
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
