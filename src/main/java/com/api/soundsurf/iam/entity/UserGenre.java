package com.api.soundsurf.iam.entity;

import com.api.soundsurf.music.entity.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_genres")
public class UserGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public UserGenre(User user, Genre genre) {
        this.user = user;
        this.genre = genre;
    }

    public static final UserGenre newInstance(User user, Genre genre) {
        return new UserGenre(user, genre);
    }
}