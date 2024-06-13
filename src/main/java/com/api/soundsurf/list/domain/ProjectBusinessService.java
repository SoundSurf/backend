package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.list.exception.DuplicateProjectNameException;
import com.api.soundsurf.music.entity.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectBusinessService {
    private final ProjectService projectService;
    private final UserService userService;

    public List<Project> find(final Long userId) {
        return projectService.find(userId);
    }

    public Project getProject(Long userId, Long projectId) {
        User user = userService.findById(userId);
        return projectService.findByIdAndUser(projectId, user);
    }

    public int getMusicCount(Project project) {
        return projectService.countMusicByProject(project);
    }

    public List<Long> getGenreIds(Project project) {
        return projectService.findGenreIdByProject(project);
    }

    public List<Music> getProjectMusics(Project project) {
        return projectService.findMusicByProject(project);
    }

    public String getProjectMusicMemo(Project project, Music music) {
        final var projectMusic = projectService.findByProjectAndMusic(project, music);
        return projectMusic != null ? projectMusic.getMemo() : null;
    }

    public Long create(final Long userId, final String name, final List<Long> genreIds) {
        validateName(userId, name);

        return projectService.create(userId, name, genreIds);
    }

    public void addMusic(final Long userId, final Long projectId, final String trackId, final String title, final String artists, final String imageUrl) {
        projectService.addMusic(userId, projectId, trackId, title, artists, imageUrl);
    }

    public void deleteMusic(final Long userId, final Long projectId, final Long musicId) {
        projectService.deleteMusic(userId, projectId, musicId);
    }

    public void addMemo(final Long userId, final Long projectId, final Long musicId, final String memo) {
        projectService.addMemo(userId, projectId, musicId, memo);
    }

    public void deleteMemo(final Long userId, final Long projectId, final Long musicId) {
        projectService.deleteMemo(userId, projectId, musicId);
    }

    public void complete(final Long userId, final Long id) {
        final var project = projectService.findNotNullable(userId, id);

        patchProject(project, true);
    }

    public void unComplete(final Long userId, final Long id) {
        final var project = projectService.findNotNullable(userId, id);

        patchProject(project, false);
    }

    public void delete(final Long userId, final Long id) {
        final var project = projectService.findNotNullable(userId, id);

        project.delete();
        projectService.save(project);
    }

    private void validateName(final Long userId, final String name) {
        if (projectService.isExist(userId, name)) {
            throw new DuplicateProjectNameException(name);
        }
    }

    private void patchProject(final Project project, final boolean isComplete) {
        if (isComplete) {
            project.complete();
        } else {
            project.unComplete();
        }

        projectService.save(project);
    }
}
