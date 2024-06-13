package com.api.soundsurf.music.entity;

import com.api.soundsurf.list.entity.Playlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "playlist_genres")
public class PlaylistGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "genre_id")
    private Long genreId;

    public PlaylistGenre(Playlist playlist, Long genreId) {
        this.playlist = playlist;
        this.genreId = genreId;
    }

    public static PlaylistGenre newInstance(final Playlist playlist, final Long genreId) {
        return new PlaylistGenre(playlist, genreId);
    }
}
