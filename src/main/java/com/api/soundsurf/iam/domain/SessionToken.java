package com.api.soundsurf.iam.domain;

import com.api.soundsurf.api.config.LocalDateTimeUtcSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_tokens")
@Getter
@Setter
public class SessionToken implements Persistable<Long> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_uuid", nullable = false)
    private String userUuid;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    LocalDateTime expiredAt;

    @PrePersist
    private void onPersist() {
        this.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
