package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.dto.QrDto;
import com.api.soundsurf.iam.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QrTransferService {
    private final QrbusinessService businessService;

    @Transactional
    public QrDto.Create.Response create(final SessionUser sessionUser) {
        return new QrDto.Create.Response(businessService.create(sessionUser));
    }

}
