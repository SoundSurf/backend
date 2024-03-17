package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class UserDto {
    public static class Create {
        @Getter
        @Schema(name = "UserDto.Create.Request")
        public static class Request {
            private String username;
            @Length(min = 8)
            private String password;
            private String email;
        }

        @AllArgsConstructor
        @Getter
        public static class Response {
            private String uuid;
        }
    }
}
