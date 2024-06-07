package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class UserProfileDto {

    public static class Qr {
        @Getter
        @Schema(name = "ProfileDto.Qr.Response")
        @AllArgsConstructor
        public static class Response {
            private String qrS3Path;
        }
    }

    public static class Update {
        @Getter
        @Schema(name = "ProfileDto.Update.Request")
        public static class Request {
            private Long carId;
            @Size(min = 1, max = 3)
            private List<Long> genreIds;
            private String imageS3BucketPath;
            @Length(max=20)
            private String nickname;
        }
    }
    public static class Get {
        @Getter
        @Schema(name= "ProfileDto.Get.Response")
        @AllArgsConstructor
        public static class Response {
            private String imageStr;
        }
    }
}
