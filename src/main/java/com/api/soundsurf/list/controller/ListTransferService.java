package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTransferService {
    public void get(final SessionUser sessionUser) {
        System.out.println(sessionUser.getUserId());
    }
}
