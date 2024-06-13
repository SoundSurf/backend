package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.domain.ProjectBusinessService;
import com.api.soundsurf.list.dto.PlaylistDto;
import com.api.soundsurf.list.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectTransferService {
    private final ProjectBusinessService businessService;

    public PlaylistDto.List.Response getProjectList(final SessionUser sessionUser) {
        final var projects = businessService.find(sessionUser.getUserId());

        final List<PlaylistDto.List.PlaylistSummary> completeProjects = projects.stream()
                .filter(Project::isComplete)
                .map(project -> new PlaylistDto.List.PlaylistSummary(
                        project.getName(),
                        project.getCreatedAt(),
                        project.getProjectMusics().size()))
                .collect(Collectors.toList());

        final List<PlaylistDto.List.PlaylistSummary> unCompleteProjects = projects.stream()
                .filter(project -> !project.isComplete())
                .map(project -> new PlaylistDto.List.PlaylistSummary(
                        project.getName(),
                        project.getCreatedAt(),
                        project.getProjectMusics().size()))
                .collect(Collectors.toList());

        return new PlaylistDto.List.Response(completeProjects, unCompleteProjects);
    }

    public PlaylistDto.Get.Response getProject(final SessionUser sessionUser, final Long projectId) {
        final var project = businessService.getProject(sessionUser.getUserId(), projectId);
        final var musicCount = businessService.getMusicCount(project);
        final var genreIds = businessService.getGenreIds(project);
        final var projectMusics = businessService.getProjectMusics(project);

        List<PlaylistDto.Get.MusicWithMemo> projectMusicsWithMemo = projectMusics.stream()
                .map(music -> {
                    final var memo = businessService.getProjectMusicMemo(project, music);
                    return new PlaylistDto.Get.MusicWithMemo(music, memo);
                })
                .collect(Collectors.toList());

        return new PlaylistDto.Get.Response(
                project.getId(),
                project.getName(),
                project.isComplete(),
                project.isDeleted(),
                project.getCreatedAt(),
                musicCount,
                genreIds,
                projectMusicsWithMemo
        );
    }

    public PlaylistDto.Create.Response create(final SessionUser sessionUser, final PlaylistDto.Create.Request req) {
        final var projectId = businessService.create(sessionUser.getUserId(), req.getName(), req.getGenreIds());

        return new PlaylistDto.Create.Response(projectId);
    }

    public void addMusic(final SessionUser sessionUser, final Long projectId, final PlaylistDto.Music.Request req) {
        businessService.addMusic(sessionUser.getUserId(), projectId, req.getTrackId(), req.getTitle(), req.getArtists(), req.getImageUrl());
    }

    public void deleteMusic(final SessionUser sessionUser, final Long projectId, final Long musicId) {
        businessService.deleteMusic(sessionUser.getUserId(), projectId, musicId);
    }

    public void addMemo(final SessionUser sessionUser, final Long projectId, final PlaylistDto.Memo.Request req) {
        businessService.addMemo(sessionUser.getUserId(), projectId, req.getMusicId(), req.getMemo());
    }

    public void deleteMemo(final SessionUser sessionUser, final Long projectId, final Long musicId) {
        businessService.deleteMemo(sessionUser.getUserId(), projectId, musicId);
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
}
