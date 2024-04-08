package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.UserTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class UserController {
    private final UserTransferService transferService;

    @PostMapping(value = "/create")
    public UserDto.Create.Response create(final @Valid @RequestBody UserDto.Create.Request request) {
        return transferService.create(request);
    }

    @PostMapping(value = "/login")
    public UserDto.Login.Response login(final @Valid @RequestBody UserDto.Login.Request request) {
        return transferService.login(request);
    }

    @GetMapping("")
    public UserDto.Info.Response info(final @AuthenticationPrincipal SessionUser sessionUser) {
        return transferService.info(sessionUser);
    }

}
