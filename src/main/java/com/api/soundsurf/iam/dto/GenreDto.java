package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class GenreDto {
    public static class GetAll {
        @AllArgsConstructor
        @Schema(name = "GenreDto.Get.Response")
        @Getter
        public static class Response {
            private List<Genre> genres;
        }

        @Getter
        @AllArgsConstructor
        public static class Genre{
            private Long id;
            private String name;
            private String description;
        }
    }

    public static class Select {
        @Getter
        @Schema(name = "GenreDto.Select.Request")
        public static class Request {
            @NotNull
            @Size(min = 1, max = 3)
            private List<Long> ids;
        }

        @AllArgsConstructor
        @Getter
        @Schema(name = "GenreDto.Select.Response")
        public static class Response {
            private List<String> names;
        }
    }
}
