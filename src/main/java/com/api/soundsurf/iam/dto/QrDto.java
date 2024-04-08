package com.api.soundsurf.iam.dto;

import com.api.soundsurf.iam.entity.Qr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    public static class find {
        @Getter
        @Schema(name = "QrDto.find.Request")
        @Builder
        public static class Response {
            private Long id;
            private byte[] qrCode;
            private Long userId;

            public static Response of(Qr qr) {
                return Response.builder()
                        .id(qr.getId())
                        .qrCode(qr.getQr())
                        .userId(qr.getUser().getId())
                        .build();
            }

        }
    }
}
