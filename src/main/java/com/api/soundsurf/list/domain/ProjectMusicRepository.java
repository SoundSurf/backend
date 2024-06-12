package com.api.soundsurf.list.domain;

import com.api.soundsurf.music.entity.ProjectMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMusicRepository extends JpaRepository<ProjectMusic, Long> {
}
