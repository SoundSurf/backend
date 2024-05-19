package com.api.soundsurf.music.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.spotify.SpotifyTransferService;
import com.api.soundsurf.music.domain.track.TrackTransferService;
import com.api.soundsurf.music.dto.MusicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/track")
public class TrackController {
    private final TrackTransferService transferService;
    private final SpotifyTransferService spotifyTransferService;

    @GetMapping("/previous")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Track previous(
            final @AuthenticationPrincipal SessionUser sessionUser, final @RequestParam(required = false) Long logId) {

        return transferService.previous(sessionUser, logId);
    }

    @GetMapping("/following")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Track following(
            final @AuthenticationPrincipal SessionUser sessionUser, final @RequestParam(required = false) Long logId) {

        return transferService.following(sessionUser, logId);
    }


    @PostMapping("/play-with-id")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"), description = "use only when session is reconnected")
            })
    public MusicDto.Track playWithId(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid @RequestBody MusicDto.Play.Request req) {
        return spotifyTransferService.playWithId(sessionUser, req);
    }
}
