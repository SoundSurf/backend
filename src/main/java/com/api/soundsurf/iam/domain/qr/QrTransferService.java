package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.api.utils.StringByteConverter;
import com.api.soundsurf.iam.dto.QrDto;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.entity.Qr;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QrTransferService {
    private final QrbusinessService businessService;
    private final StringByteConverter stringByteConverter;

    @Transactional
    public Qr create(final String userEmail) {
        return businessService.create(userEmail);
    }

    @Transactional
    public QrDto.find.Response find(final SessionUser sessionUser) {
        final var qr = businessService.getByUserId(sessionUser.getUserId());
        final var qrCodeStr = stringByteConverter.byteToString(qr.getQr());

        return QrDto.find.Response.from(qr, qrCodeStr, sessionUser.getUserId());
    }

}
