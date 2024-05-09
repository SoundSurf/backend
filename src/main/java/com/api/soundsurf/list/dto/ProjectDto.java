package com.api.soundsurf.list.dto;

import com.api.soundsurf.list.constant.EntityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class ProjectDto {
    @AllArgsConstructor

    public static class Project {
        private String name;
        private String memo;
        private String genre;

        public Project(final com.api.soundsurf.list.entity.Project project, final String gerneString){
            this.name = project.getName();
            this.memo = project.getMemo();
            this.genre = gerneString;
        }
    }

    public static class List{

        @Schema(name = "ProjectDto.List.Response")
        @Getter
        @AllArgsConstructor
        public static class Response {
            private java.util.List<Project> completed;
            private java.util.List<Project> unCompleted;
        }
    }

    public static class  Create {
        @Schema(name = "ProjectDto.Create.Request")
        @Getter
        public static class Request {
            @Length(max = 50)
            @NotEmpty
            private String name;
            private Long genreId;
        }

        @Schema(name = "ProjectDto.Create.Response")
        @AllArgsConstructor
        @Getter
        public static class Response {
            private Long id;
        }
    }

    public static class Update {
        @Schema(name = "ProjectDto.Update.Request")
        @Getter
        public static class Request {
            @Length(max = 50)
            private String name;
            @Length()
            private String memo;
        }
    }

    public static class SaveEntity {
        @Schema(name = "ProjectDto.SaveEntity.Request")
        @Getter
        public static class Request{
            private EntityType entityType;
        }
    }
}
