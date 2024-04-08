package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.QrProcessor;
import com.api.soundsurf.iam.domain.UserRepository;
import com.api.soundsurf.iam.entity.Qr;
import com.api.soundsurf.iam.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrbusinessService {
    private final QrService service;
    private final QrProcessor processor;
    private final UserRepository userRepository;

    public void create(Long userId) {
        byte[] qrCode = processor.generateQrCode(userId);
        User user = userRepository.findById(userId).get();
        service.create(new Qr(qrCode, user));
    }

    public Qr getByUserId(Long userId) {
        return service.findByUserId(userId);
    }

}
