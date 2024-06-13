package com.api.soundsurf.music.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "musics")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String trackId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    public Music(final String trackId, final String title, final String artist, final String imageUrl) {
        this.trackId = trackId;
        this.title = title;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }

    public static Music newInstance(final String trackId, final String title, final String artist, final String imageUrl) {
        return new Music(trackId, title, artist, imageUrl);
    }
}