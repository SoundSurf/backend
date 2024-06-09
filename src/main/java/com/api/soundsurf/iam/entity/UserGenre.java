package com.api.soundsurf.iam.entity;

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

    @Column(name = "genre_id")
    private Integer genreId;

    public UserGenre(User user, Integer genreId) {
        this.user = user;
        this.genreId = genreId;
    }

    public static UserGenre newInstance(final User user,final int genre) {
        return new UserGenre(user, genre);
    }
}
