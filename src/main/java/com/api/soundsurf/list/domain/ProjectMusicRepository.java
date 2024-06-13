package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.music.entity.Music;
import com.api.soundsurf.music.entity.ProjectMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectMusicRepository extends JpaRepository<ProjectMusic, Long> {
    ProjectMusic findByProjectAndMusic(Project project, Music music);

    Integer countByProject(Project project);

    @Query("SELECT pm.music FROM ProjectMusic pm WHERE pm.project = :project")
    List<Music> findMusicByProject(@Param("project") Project project);
}
