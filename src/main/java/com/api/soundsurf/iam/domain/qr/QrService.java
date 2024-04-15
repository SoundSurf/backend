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
        return repository.save(qr);
    }

}
