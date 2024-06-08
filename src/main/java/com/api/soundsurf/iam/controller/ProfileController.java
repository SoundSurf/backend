package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.car.CarTransferService;
import com.api.soundsurf.iam.domain.userProfile.UserProfileTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.music.domain.SavedMusicService;
import com.api.soundsurf.music.domain.genre.GenreTransferService;
import com.api.soundsurf.iam.dto.CarDto;
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
public class ProfileController {
    private final CarTransferService carTransferService;
    private final GenreTransferService genreTransferService;
    private final UserProfileTransferService userProfileTransferService;
    private final SavedMusicTransferService savedMusicTransferService;
    private final SavedMusicService savedMusicService;

    @GetMapping(value = "/cars/all")
    public CarDto.GetAll.Response getAllCars() {
        return carTransferService.getAllCars();
    }

    @GetMapping(value = "/genres/all")
    public GenreDto.GetAll.Response getAllGenres() {
        return genreTransferService.getAllGenres();
    }

    @PatchMapping(value = "/car/select")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void selectCar(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody CarDto.Select.Request request) {
        carTransferService.selectCar(sessionUser, request);
    }

    @GetMapping(value = "/user-car")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public CarDto.GetUserCar.Response getUserCar(final @AuthenticationPrincipal SessionUser sessionUser) {
        return carTransferService.getUserCar(sessionUser);
    }

    @PostMapping(value = "/genre")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public GenreDto.Select.Response selectGenre(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody GenreDto.Select.Request request) {
        return genreTransferService.selectGenre(sessionUser, request);
    }

    @PostMapping(value = "/image")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void uploadImage(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody UserProfileDto.Image.Request request) {
        userProfileTransferService.upload(sessionUser, request);
    }

    @GetMapping(value = "/image")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public UserProfileDto.Get.Response getImage(final @AuthenticationPrincipal SessionUser sessionUser) {
        return userProfileTransferService.get(sessionUser);
    }

    @GetMapping(value = "/saved-musics")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public SavedMusicDto.GetAll.Response getSavedMusics(final @AuthenticationPrincipal SessionUser sessionUser) {
        return savedMusicTransferService.getSavedMusics(sessionUser);
    }

    @PostMapping
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public void save(final @AuthenticationPrincipal SessionUser sessionUser, final @RequestParam Long musicId) {
        savedMusicService.saveMusic(sessionUser.getUserId(), musicId);
    }
}
