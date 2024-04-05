package com.api.soundsurf.iam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "GENRE")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private byte[] image;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenre> userGenres = new ArrayList<>();

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    public Genre(byte[] image, String name, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public static final Genre newInstance(byte[] image, String name, String description) {
        return new Genre(image, name, description);
    }
}