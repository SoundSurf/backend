package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.ProfileDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileTransferService {
    private final ProfileBusinessService businessService;

    @Transactional
    public ProfileDto.Create.Response create(final ProfileDto.Create.Request requestDto) {
        return new ProfileDto.Create.Response(businessService.create(requestDto.getUserId(), requestDto.getNickname()));
    }
}
