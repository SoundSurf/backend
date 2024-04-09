package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UserProfileDto {
    public static class Image {
        @Getter
        @Schema(name = "ProfileDto.Image.Request")
        public static class Request{
            @NotNull
            private byte[] image;
        }

    }
}
