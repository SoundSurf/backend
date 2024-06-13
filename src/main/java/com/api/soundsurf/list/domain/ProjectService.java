package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.ProjectNotFoundException;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.music.domain.MusicRepository;
import com.api.soundsurf.music.domain.genre.ProjectGenreRepository;
import com.api.soundsurf.music.entity.Music;
import com.api.soundsurf.music.entity.ProjectGenre;
import com.api.soundsurf.music.entity.ProjectMusic;
import com.api.soundsurf.music.exception.MusicNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMusicRepository projectMusicRepository;
    private final ProjectGenreRepository projectGenreRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    public void save(final Project project) {
        projectRepository.save(project);
    }

    public boolean isExist(final Long userId, final String name) {
        return projectRepository.existsByUserIdAndName(userId, name);
    }

    public Long create(final Long userId, final String name, final List<Long> genreIds) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var newProject = projectRepository.save(new Project(user, name));
        genreIds.forEach(genreId -> projectGenreRepository.save(new ProjectGenre(newProject, genreId)));
        return newProject.getId();
    }

    public void addMusic(final Long userId, final Long projectId, final String trackId, final String title, final String artists, final String imageUrl) {
        final var music = musicRepository.save(new Music(trackId, title, artists, imageUrl));
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var project = projectRepository.findByIdAndUser(projectId, user).orElseThrow(() -> new ProjectNotFoundException(projectId));
        projectMusicRepository.save(new ProjectMusic(null, music, project));
    }

    @Transactional
    public void deleteMusic(final Long userId, final Long projectId, final Long musicId) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var project = projectRepository.findByIdAndUser(projectId, user).orElseThrow(() -> new ProjectNotFoundException(projectId));
        projectMusicRepository.deleteByPlaylistIdAndMusicId(project.getId(), musicId);
    }

    public void addMemo(final Long userId, final Long projectId, final Long musicId, final String memo) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var project = projectRepository.findByIdAndUser(projectId, user).orElseThrow(() -> new ProjectNotFoundException(projectId));
        final var music = musicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));
        final var projectMusic = projectMusicRepository.findByPlaylistAndMusic(project, music);
        projectMusic.updateMemo(memo);
        projectMusicRepository.save(projectMusic);
    }

    public void deleteMemo(final Long userId, final Long projectId, final Long musicId) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var project = projectRepository.findByIdAndUser(projectId, user).orElseThrow(() -> new ProjectNotFoundException(projectId));
        final var music = musicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));
        final var projectMusic = projectMusicRepository.findByPlaylistAndMusic(project, music);
        projectMusic.updateMemo(null);
        projectMusicRepository.save(projectMusic);
    }

    public Project findNotNullable(final Long userId, final Long id) {
        final var project = projectRepository.findByUserIdAndId(userId, id);

        if (project == null) {
            throw new ProjectNotFoundException(id);
        }

        return project;
    }

    public List<Project> find(final Long userId) {
        return projectRepository.findAllByUserIdAndIsDeletedIsFalse(userId);
    }

    public Project findByIdAndUser(Long projectId, User user) {
        return projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    public int countMusicByProject(Project project) {
        return projectMusicRepository.countByPlaylist(project);
    }

    public List<Long> findGenreIdByProject(Project project) {
        return projectGenreRepository.findGenreIdByProject(project);
    }

    public List<Music> findMusicByProject(Project project) {
        return projectMusicRepository.findMusicByPlaylist(project);
    }

    public ProjectMusic findByProjectAndMusic(Project project, Music music) {
        return projectMusicRepository.findByPlaylistAndMusic(project, music);
    }
}
