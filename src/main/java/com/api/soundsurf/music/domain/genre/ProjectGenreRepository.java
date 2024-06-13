package com.api.soundsurf.music.domain.genre;

import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.music.entity.ProjectGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectGenreRepository extends JpaRepository<ProjectGenre, Long> {
    @Query("SELECT pg.genreId FROM ProjectGenre pg WHERE pg.playlist = :project")
    List<Long> findGenreIdByProject(@Param("project") Project project);
}
