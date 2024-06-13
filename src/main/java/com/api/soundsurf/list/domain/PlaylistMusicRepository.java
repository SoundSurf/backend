package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Playlist;
import com.api.soundsurf.music.entity.Music;
import com.api.soundsurf.music.entity.PlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {
    PlaylistMusic findByPlaylistAndMusic(Playlist playlist, Music music);

    Integer countByPlaylist(Playlist playlist);

    @Query("SELECT pm.music FROM PlaylistMusic pm WHERE pm.playlist = :playlist")
    List<Music> findMusicByPlaylist(@Param("playlist") Playlist playlist);

    void deleteByPlaylistIdAndMusicId(Long playlistId, Long musicId);
}
