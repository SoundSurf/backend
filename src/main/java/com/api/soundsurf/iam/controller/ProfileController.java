package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.CarTransferService;
import com.api.soundsurf.iam.dto.CarDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
