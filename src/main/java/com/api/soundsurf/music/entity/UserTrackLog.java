package com.api.soundsurf.music.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_track_logs")
public class UserTrackLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "track_id")
    private String trackId;

    @Column(nullable = false, name = "created_at")
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    public UserTrackLog(final Long userId, final String trackId) {
        this.userId = userId;
        this.trackId = trackId;
        this.createdAt = LocalDateTime.now();
    }

    public static UserTrackLog newInstance(final Long userId, final String trackId) {
        return new UserTrackLog(userId, trackId);
    }

    public void nowPlay() {
        this.createdAt = LocalDateTime.now();
    }
}
