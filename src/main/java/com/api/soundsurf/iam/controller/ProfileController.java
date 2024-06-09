package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.user.UserTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.music.domain.SavedMusicService;
import com.api.soundsurf.music.domain.genre.GenreTransferService;
import com.api.soundsurf.iam.dto.GenreDto;
import com.api.soundsurf.music.domain.SavedMusicTransferService;
import com.api.soundsurf.music.dto.SavedMusicDto;
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
@RequestMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@CrossOrigin("*")
public class ProfileController {
    private final GenreTransferService genreTransferService;
    private final UserTransferService userTransferService;

    @PatchMapping(value = "")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void updateProfile(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody UserProfileDto.Update.Request request) {
        userTransferService.update(sessionUser, request);
    }

    @GetMapping(value = "/qr")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public UserProfileDto.Qr.Response getQr(final @AuthenticationPrincipal SessionUser sessionUser) {
        return userTransferService.getQr(sessionUser);
    }

    @GetMapping(value = "/genres/all")
    public GenreDto.GetAll.Response getAllGenres() {
        return genreTransferService.getAllGenres();
    }

}
