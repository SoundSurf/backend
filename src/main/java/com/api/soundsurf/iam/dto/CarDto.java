package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CarDto {
    public static class GetAll {
        @AllArgsConstructor
        @Schema(name = "CarDto.Get.Response")
        @Getter
        public static class Response {
            private Long id;
            private byte[] image;
            private String name;
            private String description;
        }
    }

    public static class Select {
        @Getter
        @Schema(name = "CarDto.Select.Request")
        public static class Request {
            @NotNull
            private Long id;
        }

    }
}
