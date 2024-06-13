package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.music.entity.Music;
import com.api.soundsurf.music.entity.ProjectMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectMusicRepository extends JpaRepository<ProjectMusic, Long> {
    ProjectMusic findByPlaylistAndMusic(Project playlist, Music music);

    Integer countByPlaylist(Project playlist);

    @Query("SELECT pm.music FROM ProjectMusic pm WHERE pm.playlist = :playlist")
    List<Music> findMusicByPlaylist(@Param("playlist") Project playlist);

    void deleteByPlaylistIdAndMusicId(Long playlistId, Long musicId);
}
