package com.api.soundsurf.music.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(nullable = true)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDate releasedDate;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    public Music(final String title, final String artist, final String album, final String imageUrl, final LocalDate releasedDate) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.imageUrl = imageUrl;
        this.releasedDate = releasedDate;
    }

    public static Music newInstance(final String title, final String artist, final String album, final String imageUrl, final LocalDate releasedDate) {
        return new Music(title, artist, album, imageUrl, releasedDate);
    }
}