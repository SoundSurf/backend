package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.QrProcessor;
import com.api.soundsurf.iam.domain.UserRepository;
import com.api.soundsurf.iam.dto.SessionUser;
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

    public byte[] create(SessionUser sessionUser) {
        Long userId = sessionUser.getUserId();
        byte[] qrCode = processor.generateQrCode(userId);
        User user = userRepository.findById(userId).get();
        return service.create(new Qr(qrCode, user)).getQr();
    }

}
