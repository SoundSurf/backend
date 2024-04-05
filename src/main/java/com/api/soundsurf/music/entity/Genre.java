package com.api.soundsurf.music.entity;

import com.api.soundsurf.iam.entity.UserGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "genres", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenre> userGenres = new ArrayList<>();

    @OneToMany(mappedBy = "genres", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    public Genre(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public static Genre newInstance(final String name, final String description) {
        return new Genre(name, description);
    }
}
