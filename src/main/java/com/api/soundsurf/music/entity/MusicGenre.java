package com.api.soundsurf.music.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "music_genres")
public class MusicGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public MusicGenre(final Music music, final Genre genre) {
        this.music = music;
        this.genre = genre;
    }

    public static MusicGenre newInstance(final Music music, final Genre genre) {
        return new MusicGenre(music, genre);
    }
}
