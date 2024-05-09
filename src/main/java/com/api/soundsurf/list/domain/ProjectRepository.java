package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByUserIdAndName(Long userId, String name);
    Project findByUserIdAndId(Long userId, Long id);
}
