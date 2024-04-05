package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
