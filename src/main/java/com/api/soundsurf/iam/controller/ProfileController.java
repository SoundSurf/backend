package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.ProfileTransferService;
import com.api.soundsurf.iam.dto.ProfileDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ProfileController {
    private final ProfileTransferService transferService;
    @PostMapping(value = "/nickname")
    public ProfileDto.Create.Response createNickname(final @Valid @RequestBody ProfileDto.Create.Request request) {
        return transferService.create(request);
    }
}
