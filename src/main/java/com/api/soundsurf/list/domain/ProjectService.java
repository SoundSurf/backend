package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.exception.ProjectNotFoundException;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.music.domain.MusicRepository;
import com.api.soundsurf.music.domain.genre.ProjectGenreRepository;
import com.api.soundsurf.music.entity.Music;
import com.api.soundsurf.music.entity.ProjectGenre;
import com.api.soundsurf.music.entity.ProjectMusic;
import com.api.soundsurf.music.exception.MusicNotFoundException;
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

//    public void save(final Project project) {
//        repository.save(project);
//    }
//
    public boolean isExist(final Long userId, final String name) {
        return projectRepository.existsByUserIdAndName(userId, name);
    }
//
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
//
//    public Project findNotNullable(final Long userId, final Long id) {
//        final var project = repository.findByUserIdAndId(userId, id);
//
//        if (project == null) {
//            throw new ProjectNotFoundException(id);
//        }
//
//        return project;
//    }
//
//    public List<Project> find(final Long userId) {
//        return repository.findAllByUserIdAndDeletedIsFalse(userId);
//    }

}
