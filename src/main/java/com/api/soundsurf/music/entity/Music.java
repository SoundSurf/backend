package com.api.soundsurf.music.entity;

import com.api.soundsurf.api.config.LocalDateTimeUtcSerializer;
import com.api.soundsurf.list.entity.SavedMusic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "musics")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = true)
    private String album;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = true)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime releasedDate;

    @OneToMany(mappedBy = "musics", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    @OneToMany(mappedBy = "musics", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedMusic> savedMusics = new ArrayList<>();

    public Music(final String title, final String artist, final String album, final String imageUrl, final LocalDateTime releasedDate) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.imageUrl = imageUrl;
        this.releasedDate = releasedDate;
    }

    public static Music newInstance(final String title, final String artist, final String album, final String imageUrl, final LocalDateTime releasedDate) {
        return new Music(title, artist, album, imageUrl, releasedDate);
    }
}