package com.api.soundsurf.iam.domain.qr;

import com.api.soundsurf.iam.entity.Qr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrRepository extends JpaRepository<Qr, Long> {
    public Optional<Qr> findByUserId(Long userId);
}
