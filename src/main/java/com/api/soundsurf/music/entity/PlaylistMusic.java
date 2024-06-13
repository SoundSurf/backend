package com.api.soundsurf.music.entity;

import com.api.soundsurf.list.entity.Playlist;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "playlist_musics")
public class PlaylistMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String memo;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Music music;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public PlaylistMusic(final String meno, final Music music, final Playlist playlist) {
        this.memo = meno;
        this.music = music;
        this.playlist = playlist;
    }

    public static PlaylistMusic newInstance(final String memo, final Music music, final Playlist playlist) {
        return new PlaylistMusic(memo, music, playlist);
    }

    public void updateMemo(final String memo) {
        this.memo = memo;
    }
}
