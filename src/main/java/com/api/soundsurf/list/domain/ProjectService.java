package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.list.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private ProjectRepository repository;

    public void save(final Project project) {
        repository.save(project);
    }

    public boolean isExist(final Long userId, final String name) {
        return repository.existsByUserIdAndName(userId, name);
    }

    public Long create(final Long userId, final String name, final Long genreId){
        final var newProject = repository.save(new Project(userId, name, genreId));

        return newProject.getId();
    }

    public Project findNotNullable(final Long userId, final Long id) {
        final var project = repository.findByUserIdAndId(userId, id);

        if (project == null) {
            throw new ProjectNotFoundException(id);
        }

        return project;
    }

}
