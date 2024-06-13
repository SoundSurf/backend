package com.api.soundsurf.music.domain.genre;

import com.api.soundsurf.list.entity.Playlist;
import com.api.soundsurf.music.entity.PlaylistGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistGenreRepository extends JpaRepository<PlaylistGenre, Long> {
    @Query("SELECT pg.genreId FROM PlaylistGenre pg WHERE pg.playlist = :playlist")
    List<Long> findGenreIdByPlaylist(@Param("playlist") Playlist playlist);
}
