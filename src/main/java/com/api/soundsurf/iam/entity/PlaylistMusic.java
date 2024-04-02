package com.api.soundsurf.iam.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PLAYLIST_MUSIC")
public class PlaylistMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public PlaylistMusic(Music music, Playlist playlist) {
        this.music = music;
        this.playlist = playlist;
    }

    public static final PlaylistMusic newInstance(Music music, Playlist playlist) {
        return new PlaylistMusic(music, playlist);
    }
}
