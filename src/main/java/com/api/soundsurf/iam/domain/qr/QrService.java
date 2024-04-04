package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.entity.Qr;
import com.api.soundsurf.iam.exception.QrExistException;
import com.api.soundsurf.iam.exception.QrNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrService {
    private final QrRepository repository;

    public Qr create(final Qr qr) {
        if (repository.findByUserId(qr.getUser().getId()).isPresent()) {
            throw new QrExistException();
        }
        return repository.save(qr);
    }

    public Qr findByUserId(final Long userId) {
        return repository.findByUserId(userId).orElseThrow(QrNotFoundException::new);
    }

}
