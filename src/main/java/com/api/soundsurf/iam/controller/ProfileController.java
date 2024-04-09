package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.car.CarTransferService;
import com.api.soundsurf.iam.domain.userProfile.UserProfileTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.music.domain.GenreTransferService;
import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.dto.GenreDto;
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

    @GetMapping(value = "/cars/all")
    public CarDto.GetAll.Response getAllCars() {
        return carTransferService.getAllCars();
    }

    @GetMapping(value = "/genres/all")
    public GenreDto.GetAll.Response getAllGenres() {
        return genreTransferService.getAllGenres();
    }

    @PatchMapping(value = "/car/select")
    public void selectCar(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody CarDto.Select.Request request) {
        carTransferService.selectCar(sessionUser, request);
    }

    @PatchMapping(value = "/car/cancel")
    public void cancelCar(final @AuthenticationPrincipal SessionUser sessionUser) {
        carTransferService.cancelCar(sessionUser);
    }

    @GetMapping(value = "/user-car")
    public CarDto.GetUserCar.Response getUserCar(final @AuthenticationPrincipal SessionUser sessionUser) {
        return carTransferService.getUserCar(sessionUser);
    }

    @PostMapping(value = "/genre")
    public GenreDto.Select.Response selectGenre(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody GenreDto.Select.Request request) {
        return genreTransferService.selectGenre(sessionUser, request);
    }

    @PostMapping(value = "/image")
    public void uploadImage(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody UserProfileDto.Image.Request request) {
        userProfileTransferService.upload(sessionUser, request);
    }
}
