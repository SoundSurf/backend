package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByUserIdAndName(Long userId, String name);
    Optional<Project> findByIdAndUser(Long id, User user);
    Project findByUserIdAndId(Long userId, Long id);
    List<Project> findAllByUserIdAndDeletedIsFalse(Long userId);
}
