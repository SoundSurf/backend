package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.car.CarTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.GenreTransferService;
import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.dto.GenreDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ProfileController {
    private final CarTransferService carTransferService;
    private final GenreTransferService genreTransferService;

    @GetMapping(value = "/cars")
    public List<CarDto.GetAll.Response> getAllCars() {
        return carTransferService.getAllCars();
    }

    @GetMapping(value = "/genres")
    public GenreDto.GetAll.Response getAllGenres() {
        return genreTransferService.getAllGenres();
    }

    @PatchMapping(value = "/select-car")
    public void selectCar(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody CarDto.Select.Request request) {
        carTransferService.selectCar(sessionUser, request);
    }

    @PatchMapping(value = "/change-car")
    public void cancelCar(final @AuthenticationPrincipal SessionUser sessionUser) {
        carTransferService.cancelCar(sessionUser);
    }

    @PostMapping(value = "/select-genre")
    public GenreDto.Select.Response selectGenre(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody GenreDto.Select.Request request) {
        return genreTransferService.selectGenre(sessionUser, request);
    }
}
