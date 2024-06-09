package com.api.soundsurf.iam.domain.user;


import com.api.soundsurf.iam.QrProcessor;
import com.api.soundsurf.iam.domain.SessionTokenService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserTransferService {
    private final UserBusinessService businessService;
    private final SessionTokenService sessionTokenService;
    private final UserTrackLogService userTrackLogService;
    private final QrProcessor qrProcessor;

    @Transactional
    public void update(final SessionUser sessionUser, final UserProfileDto.Update.Request requestDto) {
        businessService.update(sessionUser.getUserId(), requestDto.getCarId(), requestDto.getGenreIds(), requestDto.getNickname(), requestDto.getImageS3BucketPath());
    }

    public UserProfileDto.Qr.Response getQr(final SessionUser sessionUser) {
        final var qrS3BucketPath = businessService.getQr(sessionUser.getUserId());

        return new UserProfileDto.Qr.Response(qrS3BucketPath);
    }

    @Transactional
    public UserDto.Create.Response create(final UserDto.Create.Request requestDto) {
        final var qrS3BucketPath = qrProcessor.generateQrCode(requestDto.getEmail());
        final var userId = businessService.create(requestDto.getEmail(), requestDto.getPassword(), qrS3BucketPath);
        final var sessionToken = sessionTokenService.create(userId);

        return new UserDto.Create.Response(sessionToken.getToken());
    }

    @Transactional
    public UserDto.Login.Response login(final UserDto.Login.Request requestDto) {
        final var user = businessService.login(requestDto);

        final var sessionToken = sessionTokenService.findOrCreateNew(user.getId());

        return new UserDto.Login.Response(sessionToken.getToken());
    }

    @Transactional
    public UserDto.SetNickname.Response setNickname(final SessionUser sessionUser, final UserDto.SetNickname.Request requestDto) {
        String nickname = businessService.setNickname(sessionUser.getUserId(), requestDto.getNickname());

        return new UserDto.SetNickname.Response(nickname);
    }

    public UserDto.Info.Response info(final SessionUser sessionUser) {
        final var userId = sessionUser.getUserId();

        final var userInfo = businessService.getUser(userId);
        final var userCarId = userInfo.getCarId();
        final var userProfile = userInfo.getImageS3BucketPath();

        final var prevTrack = userTrackLogService.findPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));

        return new UserDto.Info.Response(userInfo, userCarId, userProfile, prevTrack.getTrackId());
    }

}
