package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.qr.QrTransferService;
import com.api.soundsurf.iam.dto.QrDto;
import com.api.soundsurf.iam.dto.SessionUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/qr", produces = {MediaType.APPLICATION_JSON_VALUE})
public class QrController {
    private final QrTransferService transferService;

    @GetMapping
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true)
            })
    public QrDto.find.Response find(@AuthenticationPrincipal SessionUser sessionUser) {
        return transferService.find(sessionUser);
    }

}
