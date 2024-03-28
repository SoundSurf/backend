package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class UserDto {
    public static class Create {
        @Getter
        @Schema(name = "UserDto.Create.Request")
        public static class Request {
            @Email
            private String email;
            @Length(min = 6, max = 16)
            private String password;
        }

        @AllArgsConstructor
        @Schema(name = "UserDto.Create.Response")
        @Getter
        public static class Response {
            private String userToken;
        }
    }
}
