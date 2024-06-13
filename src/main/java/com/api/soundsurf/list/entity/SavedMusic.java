package com.api.soundsurf.list.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.domain.spotify.Utils;
import com.api.soundsurf.music.dto.MusicDto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "saved_musics")
public class SavedMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "track_id")
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

    @Column(name = "album_metadata", nullable = false)
    private String albumMetadata;

    @Column(name = "saved_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime savedAt;

    @PrePersist
    private void onPersist() {
        if (this.getSavedAt() == null) {
            this.savedAt = LocalDateTime.now();
        }
    }

    public SavedMusic(final User user, final MusicDto.Common.Song trackDto, String[] genre) {
        this.user = user;
        this.trackId = trackDto.id();
        this.trackName = trackDto.name();
        this.trackPreviewUrl = trackDto.previewUrl();
        this.trackSpotifyUrl = trackDto.spotifyUrl();
        this.trackDurationMs = trackDto.durationMs();

        final var genreString = String.join(", ", genre);
        this.albumMetadata = Utils.albumDtoToString(trackDto.album(), genreString, "");

        final var jsonMusicians = new JSONArray();
        trackDto.artists().forEach(e -> Utils.hydrateJsonMusicians(e, jsonMusicians));
        this.artistsMetadata = Utils.musicianJsonToString(jsonMusicians);

        this.savedAt = LocalDateTime.now();
    }

}
