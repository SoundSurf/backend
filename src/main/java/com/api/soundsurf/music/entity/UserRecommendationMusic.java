package com.api.soundsurf.music.entity;

import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.domain.spotify.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_recommendation_muscis")
public class UserRecommendationMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long order;

    @Column(name = "track_id", nullable = false)
    private String trackId;

    @Column(name = "track_name", nullable = false)
    private String trackName;

    @Column(name = "track_preview_url", nullable = true)
    private String trackPreviewUrl;

    @Column(name = "track_spotify_url", nullable = true)
    private String trackSpotifyUrl;

    @Column(name = "track_duration_ms", nullable = false)
    private Integer trackDurationMs;

    @Column(name = "artists_metadata", nullable = false)
    private String artistsMetadata;

    @Column(nullable = false)
    private boolean deleted;

    public UserRecommendationMusic(final Track track, final Long order, final Long userId, final String artistsMetadata) {
        this.userId = userId;
        this.order = order;
        this.trackId = track.getId();
        this.trackName = track.getName();
        this.trackPreviewUrl = track.getPreviewUrl();
        this.trackSpotifyUrl = track.getExternalUrls().getExternalUrls().get("spotify");
        this.trackDurationMs = track.getDurationMs();
        this.artistsMetadata = artistsMetadata;
    }

    public void delete() {
        this.deleted = true;
    }
}
