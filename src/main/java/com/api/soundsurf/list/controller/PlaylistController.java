package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.dto.PlaylistDto;
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
@RequestMapping(value = "/playlist", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class PlaylistController {
    private final PlaylistTransferService transferService;

    @GetMapping("")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public PlaylistDto.List.Response getPlaylistList(final @AuthenticationPrincipal SessionUser sessionUser) {
        return transferService.getPlaylistList(sessionUser);
    }

    @GetMapping("/{id}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public PlaylistDto.Get.Response getPlaylist(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id) {
        return transferService.getPlaylist(sessionUser, id);
    }

    @PostMapping()
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public PlaylistDto.Create.Response create(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody PlaylistDto.Create.Request req) {
        return transferService.create(sessionUser, req);
    }

    @PostMapping(value = "/{id}/add/music")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void addMusic(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id, final @Valid @RequestBody PlaylistDto.Music.Request req) {
        transferService.addMusic(sessionUser, id, req);
    }

    @DeleteMapping(value = "/{playlistId}/delete/music/{musicId}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })

    public void deleteMusic(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long playlistId, @PathVariable Long musicId) {
        transferService.deleteMusic(sessionUser, playlistId, musicId);
    }

    @PostMapping(value = "/{id}/add/memo")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void addMemo(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long id, final @Valid @RequestBody PlaylistDto.Memo.Request req) {
        transferService.addMemo(sessionUser, id, req);
    }

    @DeleteMapping(value = "/{playlistId}/delete/memo/{musicId}")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void deleteMemo(final @AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long playlistId, @PathVariable Long musicId) {
        transferService.deleteMemo(sessionUser, playlistId, musicId);
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
}
