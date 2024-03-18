package com.api.soundsurf.iam.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionTokenTransferService {
    private final SessionTokenBusinessService businessService;

}
