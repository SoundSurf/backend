package com.api.soundsurf.iam.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionTokenBusinessService {
    private final SessionTokenService service;
}
