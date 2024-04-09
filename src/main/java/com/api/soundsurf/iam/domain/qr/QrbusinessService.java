package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.QrProcessor;
import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.entity.Qr;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrbusinessService {
    private final QrService service;
    private final QrProcessor processor;
    private final UserService userService;

    public Qr create(final String userEmail) {
        final var qrCode = processor.generateQrCode(userEmail);

        return service.create(new Qr(qrCode));
    }

    public Qr getByUserId(final Long userId) {
        return userService.findById(userId).getQr();
    }

}
