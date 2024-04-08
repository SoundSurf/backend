package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.dto.QrDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QrTransferService {
    private final QrbusinessService businessService;

    @Transactional
    public void create(final Long userId) {
        businessService.create(userId);
    }

    @Transactional
    public QrDto.find.Response find(final Long userId) {
        return QrDto.find.Response.of(businessService.getByUserId(userId));
    }

}
