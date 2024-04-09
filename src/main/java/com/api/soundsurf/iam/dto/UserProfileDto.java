package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserProfileDto {
    public static class Image {
        @Getter
        @Schema(name = "ProfileDto.Image.Request")
        public static class Request {
            @NotNull
            private String imgStr;
        }

    }

    public static class Get {
        @Getter
        @Schema(name = "ProfileDto.Get.Response")
        @AllArgsConstructor
        public static class Response {
            private String imageStr;
        }
    }
}
