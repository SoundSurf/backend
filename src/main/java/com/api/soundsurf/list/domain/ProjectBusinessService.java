package com.api.soundsurf.list.domain;

import com.api.soundsurf.list.entity.Project;
import com.api.soundsurf.list.exception.DuplicateProjectNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectBusinessService {
    private final ProjectService service;

    public List<Project> find(final Long userId) {
        return service.find(userId);
    }

    public Long create(final Long userId, final String name, final List<Long> genreIds) {
        validateName(userId, name);

        return service.create(userId, name, genreIds);
    }

    public void addMusic(final Long userId, final Long projectId, final String trackId, final String title, final String artists, final String imageUrl) {
        service.addMusic(userId, projectId, trackId, title, artists, imageUrl);
    }

    public void addMemo(final Long userId, final Long projectId, final String trackId, final String memo) {
        service.addMemo(userId, projectId, trackId, memo);
    }
//
//    public void update(final Long userId, final Long id, final String memo, final String name) {
//        final var project = service.findNotNullable(userId, id);
//
//        patchProject(project, memo, name);
//    }
//
//    public void complete(final Long userId, final Long id) {
//        final var project = service.findNotNullable(userId, id);
//
//        patchProject(project, true);
//    }
//
//    public void unComplete(final Long userId, final Long id) {
//        final var project = service.findNotNullable(userId, id);
//
//        patchProject(project, false);
//    }
//
    public void delete(final Long userId, final Long id) {
        final var project = service.findNotNullable(userId, id);

        project.delete();
        service.save(project);
    }

    private void validateName(final Long userId, final String name) {
        if (service.isExist(userId, name)) {
            throw new DuplicateProjectNameException(name);
        }
    }
//
//    private void patchProject(final Project project, final String memo, final String name) {
//        if (memo != null) {
//            project.updateMemo(memo);
//        }
//
//        if (name != null) {
//            project.updateName(name);
//        }
//
//        service.save(project);
//    }
//
//    private void patchProject(final Project project, final boolean isComplete) {
//        if (isComplete) {
//            project.complete();
//        } else {
//            project.unComplete();
//        }
//
//        service.save(project);
//    }
}
