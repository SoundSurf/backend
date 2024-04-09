package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.QrProcessor;
import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.entity.Qr;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrbusinessService {
    private final QrService service;
    private final QrProcessor processor;
    private final UserRepository userRepository;

    public void create(Long userId) {
        final var qrCode = processor.generateQrCode(userId);
        final var user = userRepository.findById(userId).get();
        service.create(new Qr(qrCode, user));
    }

    public Qr getByUserId(Long userId) {
        return service.findByUserId(userId);
    }

}
