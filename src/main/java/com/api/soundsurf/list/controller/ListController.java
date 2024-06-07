package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@CrossOrigin("*")
public class ListController {
    private final ListTransferService transferService;

    @GetMapping()
    public String create(final @AuthenticationPrincipal SessionUser sessionUser) {
        transferService.get(sessionUser);
        return "Have to develop";
    }
}
