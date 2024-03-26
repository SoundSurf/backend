package com.api.soundsurf.iam.entity;

import com.api.soundsurf.api.config.LocalDateTimeUtcSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Persistable<Long> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = true)
    private String nickname;

    @Column(name="car_id", nullable = false)
    private Long carId = 1L;

    @Column(name="user_profile_id", nullable = false)
    private Long userProfileId;

    @Column(name="user_qr_id", nullable = false)
    private Long userQrId;

    @Column(name="new_user", nullable = false)
    private Boolean newUser = true;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    @PrePersist
    private void onPersist() {
        if (this.getCreatedAt() == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }
}
