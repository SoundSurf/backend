package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.domain.ProjectBusinessService;
import com.api.soundsurf.list.dto.ProjectDto;
import com.api.soundsurf.list.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectTransferService {
    private final ProjectBusinessService businessService;

//    public ProjectDto.List.Response list(final SessionUser sessionUser) {
//        final var projects = businessService.find(sessionUser.getUserId());
//
//        final var completeProjects = projects.stream()
//                .filter(Project::isComplete)
//                .map(project -> new ProjectDto.Project(project, project.getGenreId()))
//                .toList();
//        final var unCompleteProjects = projects.stream()
//                .filter(project -> !project.isComplete())
//                .map(project -> new ProjectDto.Project(project, project.getGenreId()))
//                .toList();
//
//        return new ProjectDto.List.Response(completeProjects, unCompleteProjects);
//    }
//
    public ProjectDto.Create.Response create(final SessionUser sessionUser, final ProjectDto.Create.Request req) {
        final var projectId = businessService.create(sessionUser.getUserId(), req.getName(), req.getGenreIds());

        return new ProjectDto.Create.Response(projectId);
    }

    public void addMusic(final SessionUser sessionUser, final Long projectId, final ProjectDto.Music.Request req) {
        businessService.addMusic(sessionUser.getUserId(), projectId, req.getTrackId(), req.getTitle(), req.getArtists(), req.getImageUrl());
    }
//
//    public void update(final SessionUser sessionUser, final Long id, final ProjectDto.Update.Request req) {
//        businessService.update(sessionUser.getUserId(), id, req.getMemo(), req.getName());
//    }
//
//    public void complete(final SessionUser sessionUser, final Long id) {
//        businessService.complete(sessionUser.getUserId(), id);
//    }
//
//    public void unComplete(final SessionUser sessionUser, final Long id) {
//        businessService.unComplete(sessionUser.getUserId(), id);
//    }
//
//    public void delete(final SessionUser sessionUser, final Long id) {
//        businessService.delete(sessionUser.getUserId(), id);
//    }
//
//    public void saveEntity(final SessionUser sessionUser, final Long id, final ProjectDto.SaveEntity.Request req) {
//
//        System.out.println("asdf");
//
//    }
//
////    private List<ProjectDto.Project> mapProjectsToDto(List<Project> projects, boolean isComplete, List<Long> genreIds) {
////        return projects.stream()
////                .filter(project -> project.isComplete() == isComplete)
////                .map(project -> {
//////                    final var genreName = genreMap.get(project.getGenreId());
////                    return new ProjectDto.Project(project, genreIds);
////                })
////                .toList();
////    }
}
