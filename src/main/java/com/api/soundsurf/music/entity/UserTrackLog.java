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
import java.util.List;

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

    @Column(nullable = true, name = "image_url")
    private String imageUrl;

    @Column(name = "track_preview_url", nullable = true)
    private String trackPreviewUrl;

    @Column(name = "track_spotify_url", nullable = true)
    private String trackSpotifyUrl;

    @Column(name = "track_duration_ms", nullable = false)
    private Integer trackDurationMs;

    @Column(name = "artists_metadata", nullable = true)
    private String artistsMetadata;

    @Column(name = "album_metadata", nullable = true)
    private String albumMetadata;

    @Column(nullable = true, name = "released_date")
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

    public UserTrackLog(final Long userId, final String trackId, final Long order, final String name, final List<String> images, final String previewUrl, final String spotifyUrl, final Integer durationMs, final String artistsMetadata,final String albumMetadata, final LocalDate releaseDate) {
        this.userId = userId;
        this.trackId = trackId;
        this.order = order;
        this.title = name;
        this.imageUrl = images.get(0);
        this.trackPreviewUrl = previewUrl;
        this.trackSpotifyUrl = spotifyUrl;
        this.trackDurationMs = durationMs;
        this.artistsMetadata = artistsMetadata;
        this.albumMetadata = albumMetadata;
        this.releasedDate = releaseDate;
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusYears(100);
    }

    public void nowPlay() {
        this.createdAt = LocalDateTime.now();
    }
}
