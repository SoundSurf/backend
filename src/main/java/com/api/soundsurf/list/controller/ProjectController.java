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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/project", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ProjectController {
    private final ProjectTransferService transferService;

    @GetMapping("")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public ProjectDto.List.Response getProjectList(final @AuthenticationPrincipal SessionUser sessionUser) {
        return transferService.getProjectList(sessionUser);
    }

    @GetMapping("/{id}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public ProjectDto.Get.Response getProject(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id) {
        return transferService.getProject(sessionUser, id);
    }

    @PostMapping()
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public ProjectDto.Create.Response create(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody ProjectDto.Create.Request req) {
        return transferService.create(sessionUser, req);
    }

    @PostMapping(value = "/{id}/add/music")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void addMusic(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id, final @Valid @RequestBody ProjectDto.Music.Request req) {
        transferService.addMusic(sessionUser, id, req);
    }

    @DeleteMapping(value = "/{projectId}/delete/music/{musicId}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void deleteMusic(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long projectId, @PathVariable Long musicId) {
        transferService.deleteMusic(sessionUser, projectId, musicId);
    }

    @PostMapping(value = "/{id}/add/memo")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void addMemo(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id, final @Valid @RequestBody ProjectDto.Memo.Request req) {
        transferService.addMemo(sessionUser, id, req);
    }

    @DeleteMapping(value = "/{projectId}/delete/memo/{musicId}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void deleteMemo(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long projectId, @PathVariable Long musicId) {
        transferService.deleteMemo(sessionUser, projectId, musicId);
    }
//
//    @PatchMapping("/{id}")
//    @Operation(
//            parameters = {
//                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
//                            required = true, content = @Content(mediaType = "application/json"))
//            })
//    public void update(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id, final @Valid @RequestBody ProjectDto.Update.Request req) {
//        transferService.update(sessionUser, id, req);
//    }
//
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
//
//    @PostMapping("/{id}/entity")
//    @Operation(
//            parameters = {
//                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
//                            required = true, content = @Content(mediaType = "application/json"))
//            })
//    public void saveEntity(final @AuthenticationPrincipal SessionUser sessionUser, final @PathVariable Long id, final @Valid @RequestBody ProjectDto.SaveEntity.Request req) {
//        transferService.saveEntity(sessionUser, id, req);
//    }
}
