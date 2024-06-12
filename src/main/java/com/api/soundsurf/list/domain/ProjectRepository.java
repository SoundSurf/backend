package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByUserIdAndName(Long userId, String name);
    Project findByUserIdAndId(Long userId, Long id);
    List<Project> findAllByUserIdAndDeletedIsFalse(Long userId);
}
