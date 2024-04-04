package com.api.soundsurf.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class QrDto {
    public static class Create {
        @Getter
        @Schema(name = "QrDto.Create.Request")
        @AllArgsConstructor
        public static class Response {
            private byte[] qrCode;
        }
    }
}
