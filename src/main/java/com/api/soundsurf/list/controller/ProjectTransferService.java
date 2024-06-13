package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.exception.ProjectNotFoundException;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import com.api.soundsurf.list.domain.ProjectBusinessService;
import com.api.soundsurf.list.domain.ProjectMusicRepository;
import com.api.soundsurf.list.domain.ProjectRepository;
import com.api.soundsurf.list.dto.ProjectDto;
import com.api.soundsurf.music.domain.genre.ProjectGenreRepository;
import com.api.soundsurf.music.entity.ProjectMusic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectTransferService {
    private final ProjectBusinessService businessService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMusicRepository projectMusicRepository;
    private final ProjectGenreRepository projectGenreRepository;

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

    public ProjectDto.Get.Response getProject(final SessionUser sessionUser, final Long projectId) {
        final var user = userRepository.findUserById(sessionUser.getUserId()).orElseThrow(() -> new UserNotFoundException(sessionUser.getUserId()));
        final var project = projectRepository.findByIdAndUser(projectId, user).orElseThrow(() -> new ProjectNotFoundException(projectId));
        final var musicCount = projectMusicRepository.countByProject(project);
        final var genreIds = projectGenreRepository.findGenreIdByProject(project);
        final var projectMusics = projectMusicRepository.findMusicByProject(project);

        List<ProjectDto.Get.MusicWithMemo> projectMusicsWithMemo = projectMusics.stream()
                .map(music -> {
                    ProjectMusic projectMusic = projectMusicRepository.findByProjectAndMusic(project, music);
                    String memo = projectMusic != null ? projectMusic.getMemo() : null;
                    return new ProjectDto.Get.MusicWithMemo(music, memo);
                })
                .collect(Collectors.toList());

        return new ProjectDto.Get.Response(
                project.getName(),
                project.isComplete(),
                project.isDeleted(),
                project.getCreatedAt(),
                musicCount,
                genreIds,
                projectMusicsWithMemo
        );
    }
    public ProjectDto.Create.Response create(final SessionUser sessionUser, final ProjectDto.Create.Request req) {
        final var projectId = businessService.create(sessionUser.getUserId(), req.getName(), req.getGenreIds());

        return new ProjectDto.Create.Response(projectId);
    }

    public void addMusic(final SessionUser sessionUser, final Long projectId, final ProjectDto.Music.Request req) {
        businessService.addMusic(sessionUser.getUserId(), projectId, req.getTrackId(), req.getTitle(), req.getArtists(), req.getImageUrl());
    }

    public void addMemo(final SessionUser sessionUser, final Long projectId, final ProjectDto.Memo.Request req) {
        businessService.addMemo(sessionUser.getUserId(), projectId, req.getTrackId(), req.getMemo());
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
