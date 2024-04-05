package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProfileDto {
    public static class Create {
        @Getter
        @Schema(name = "ProfileDto.Create.Request")
        public static class Request {
            @NotNull
            private Long userId;

            @NotNull
            private String nickname;
        }

        @AllArgsConstructor
        @Schema(name = "ProfileDto.Create.Response")
        @Getter
        public static class Response {
            private String nickname;
        }
    }
}
