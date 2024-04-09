package com.api.soundsurf.iam.domain.qr;

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

    @Transactional
    public Qr create(final String userEmail) {
        return businessService.create(userEmail);
    }

    @Transactional
    public QrDto.find.Response find(final SessionUser sessionUser) {
        return QrDto.find.Response.of(businessService.getByUserId(sessionUser.getUserId()));
    }

}
