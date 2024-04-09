package com.api.soundsurf.iam.dto;

import com.api.soundsurf.iam.entity.Qr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class QrDto {
    public static class find {
        @Getter
        @Schema(name = "QrDto.find.Response")
        @Builder
        public static class Response {
            private Long id;
            private String qrCode;
            private Long userId;

            public static Response from(final Qr qr, final String qrString, final Long userId) {
                return Response.builder()
                        .id(qr.getId())
                        .qrCode(qrString)
                        .userId(userId)
                        .build();
            }

        }
    }
}
