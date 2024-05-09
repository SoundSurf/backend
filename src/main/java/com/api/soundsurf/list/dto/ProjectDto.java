package com.api.soundsurf.list.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class ProjectDto {

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
}
