package com.api.soundsurf.iam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MUSIC")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private String album;

    @Column
    private String imageUrl;

    @Column
    private Date releasedDate;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedMusic> savedMusics = new ArrayList<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistMusic> playlistMusics = new ArrayList<>();

    public Music(String title, String artist, String album, String imageUrl, Date releasedDate) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.imageUrl = imageUrl;
        this.releasedDate = releasedDate;
    }

    public static final Music newInstance(String title, String artist, String album, String imageUrl, Date releasedDate) {
        return new Music(title, artist, album, imageUrl, releasedDate);
    }
}