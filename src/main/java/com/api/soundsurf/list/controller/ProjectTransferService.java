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

}
