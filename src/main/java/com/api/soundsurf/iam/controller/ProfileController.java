package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.CarTransferService;
import com.api.soundsurf.iam.dto.CarDto;
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

    @GetMapping("/cars")
    public List<CarDto.GetAll.Response> getAllCars() {
        return carTransferService.getAllCars();
    }

    @PostMapping(value = "/select-car")
    public CarDto.Select.Response selectCar(final @Valid @RequestBody CarDto.Select.Request request) {
        return carTransferService.selectCar(request);
    }
}
