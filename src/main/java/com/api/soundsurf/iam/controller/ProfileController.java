package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.CarTransferService;
import com.api.soundsurf.iam.domain.GenreTransferService;
import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.dto.GenreDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ProfileController {
    private final CarTransferService carTransferService;
    private final GenreTransferService genreTransferService;

    @GetMapping("/cars")
    public List<CarDto.GetAll.Response> getAllCars() {
        return carTransferService.getAllCars();
    }

    @PostMapping(value = "/select-car")
    public CarDto.Select.Response selectCar(final @Valid @RequestBody CarDto.Select.Request request) {
        return carTransferService.selectCar(request);
    }

    @GetMapping("/genres")
    public List<GenreDto.GetAll.Response> getAllGenres() {
        return genreTransferService.getAllGenres();
    }
}
