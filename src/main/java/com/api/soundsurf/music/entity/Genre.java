package com.api.soundsurf.music.entity;

import com.api.soundsurf.iam.entity.UserGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "genres", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenre> userGenres = new ArrayList<>();

    @OneToMany(mappedBy = "genres", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static final Genre newInstance(String name, String description) {
        return new Genre(name, description);
    }
}
