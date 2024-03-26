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
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Persistable<Long> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "locked", nullable = false)
    private boolean locked = false;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime updatedAt;

    @PrePersist
    private void onPersist() {
        if (this.getCreatedAt() == null) {
            this.createdAt = this.updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }
}
