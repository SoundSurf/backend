package com.api.soundsurf.music.entity;

import com.api.soundsurf.list.entity.Project;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "playlist_musics")
public class ProjectMusic {
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
    private Project playlist;

    public ProjectMusic(final String meno, final Music music, final Project playlist) {
        this.memo = meno;
        this.music = music;
        this.playlist = playlist;
    }

    public static ProjectMusic newInstance(final String memo, final Music music, final Project playlist) {
        return new ProjectMusic(memo, music, playlist);
    }

    public void updateMemo(final String memo) {
        this.memo = memo;
    }
}
