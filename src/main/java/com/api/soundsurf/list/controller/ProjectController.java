package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.dto.ProjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/project", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ProjectController {
    private final ProjectTransferService transferService;

    @PostMapping()
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public ProjectDto.Create.Response create(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody ProjectDto.Create.Request req) {
        return transferService.create(sessionUser, req);
    }

    @PatchMapping("/{id}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void update(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id, final @Valid @RequestBody ProjectDto.Update.Request req) {
        transferService.update(sessionUser, id,req);
    }

    @PatchMapping("/{id}/complete")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void complete(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id) {
        transferService.complete(sessionUser, id);
    }

    @PatchMapping("/{id}/un-complete")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void unComplete(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id) {
        transferService.unComplete(sessionUser, id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void delete(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id) {
        transferService.delete(sessionUser, id);
    }

//    @PostMapping("/{id}/")
}
