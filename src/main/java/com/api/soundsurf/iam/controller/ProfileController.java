package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.user.UserTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.music.domain.SavedMusicService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@CrossOrigin("*")
public class ProfileController {
    private final UserTransferService userTransferService;
    private final SavedMusicService savedMusicService;
    private final SavedMusicTransferService savedMusicTransferService;

    @PatchMapping(value = "")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void updateProfile(final @AuthenticationPrincipal SessionUser sessionUser,
                              final @Valid @RequestPart(value = "request") UserProfileDto.Update.Request request,
                              final @Valid @RequestPart(value = "image", required = false) MultipartFile image) {
        userTransferService.update(sessionUser, request, image);
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
    @PostMapping(value = "/music/save")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void save(final @AuthenticationPrincipal SessionUser sessionUser, final @RequestParam List<String> musicIds) {
        savedMusicService.saveMusic(sessionUser.getUserId(), musicIds);
    }

    @DeleteMapping(value = "/music/unsave")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void unsave(final @AuthenticationPrincipal SessionUser sessionUser, final @RequestParam String musicId) {
        savedMusicService.unsaveMusic(sessionUser.getUserId(), musicId);
    }

    @GetMapping(value = "/music/is-saved")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public SavedMusicDto.GetCount.Response getCount(final @AuthenticationPrincipal SessionUser sessionUser, final @RequestParam String musicId) {
        Boolean isSaved = savedMusicService.isSavedMusic(sessionUser.getUserId(), musicId);
        long count = savedMusicService.getSavedMusicCount(sessionUser.getUserId());
        return new SavedMusicDto.GetCount.Response(count, isSaved);
    }

    @GetMapping(value = "list/saved-musics")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public SavedMusicDto.GetAll.Response getSavedMusics(final @AuthenticationPrincipal SessionUser sessionUser) {
        return savedMusicTransferService.getSavedMusics(sessionUser);
    }
}
