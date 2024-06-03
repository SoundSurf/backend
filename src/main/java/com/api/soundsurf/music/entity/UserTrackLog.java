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

import java.time.LocalDate;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = true)
    private String album;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(name = "track_preview_url", nullable = true)
    private String trackPreviewUrl;

    @Column(name = "track_spotify_url", nullable = true)
    private String trackSpotifyUrl;

    @Column(name = "track_duration_ms", nullable = false)
    private Integer trackDurationMs;

    @Column(name = "artists_metadata", nullable = false)
    private String artistsMetadata;

    @Column(name = "album_metadata", nullable = false)
    private String albumMetadata;

    @Column(nullable = true)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDate releasedDate;

    @Column(nullable = false)
    private Long order;

    @Column(nullable = false, name = "created_at")
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "expired_at")
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime expiredAt;

    public UserTrackLog(final Long userId, final String trackId, final Long order) {
        this.userId = userId;
        this.trackId = trackId;
        this.order = order;
        this.createdAt = LocalDateTime.now();
    }

    public static UserTrackLog newInstance(final Long userId, final String trackId, final Long order) {
        return new UserTrackLog(userId, trackId, order);
    }

    public void nowPlay() {
        this.createdAt = LocalDateTime.now();
    }
}
