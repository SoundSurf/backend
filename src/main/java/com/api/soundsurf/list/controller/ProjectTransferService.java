package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.domain.ProjectBusinessService;
import com.api.soundsurf.list.dto.ProjectDto;
import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.music.domain.GenreTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectTransferService {
    private final ProjectBusinessService businessService;
    private final GenreTransferService genreTransferService;

    public ProjectDto.List.Response list(final SessionUser sessionUser) {
        final var projects = businessService.find(sessionUser.getUserId());

        final var genres = genreTransferService.getAllGenres();

        final var genreHashMap = new HashMap<Long, String>();
        genres.getGenres().forEach(genre -> {
            genreHashMap.put(genre.getId(), genre.getName());
        });

        final var completeProjects = mapProjectsToDto(projects, true, genreHashMap);
        final var unCompleteProjects = mapProjectsToDto(projects, false, genreHashMap);

        return new ProjectDto.List.Response(completeProjects, unCompleteProjects);
    }



    public ProjectDto.Create.Response create(final SessionUser sessionUser, final ProjectDto.Create.Request req) {
        final var projectId = businessService.create(sessionUser.getUserId(), req.getName(), req.getGenreId());

        return new ProjectDto.Create.Response(projectId);
    }

    public void update(final SessionUser sessionUser, final Long id, final ProjectDto.Update.Request req) {
        businessService.update(sessionUser.getUserId(), id, req.getMemo(), req.getName());
    }

    public void complete(final SessionUser sessionUser, final Long id) {
        businessService.complete(sessionUser.getUserId(), id);
    }

    public void unComplete(final SessionUser sessionUser, final Long id) {
        businessService.unComplete(sessionUser.getUserId(), id);
    }

    public void delete(final SessionUser sessionUser, final Long id) {
        businessService.delete(sessionUser.getUserId(), id);
    }

    public void saveEntity(final SessionUser sessionUser, final Long id, final ProjectDto.SaveEntity.Request req) {
        System.out.println("asdf");

    }

    private List<ProjectDto.Project> mapProjectsToDto(List<Project> projects, boolean isComplete, Map<Long, String> genreMap) {
        return projects.stream()
                .filter(project -> project.isComplete() == isComplete)
                .map(project -> {
                    final var genreName = genreMap.get(project.getGenreId());
                    return new ProjectDto.Project(project, genreName);
                })
                .toList();
    }
}
