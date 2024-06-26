package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.user.UserTransferService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@CrossOrigin("*")
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
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public UserDto.Info.Response info(final @AuthenticationPrincipal SessionUser sessionUser) {
        return transferService.info(sessionUser);
    }

    @PostMapping(value = "/nickname")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public UserDto.SetNickname.Response setNickname(final @AuthenticationPrincipal SessionUser sessionUser, final @Valid @RequestBody UserDto.SetNickname.Request request) {
        return transferService.setNickname(sessionUser, request);
    }
}
