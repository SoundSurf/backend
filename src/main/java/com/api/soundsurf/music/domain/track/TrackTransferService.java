package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.domain.userGenre.UserGenreService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.dto.MusicDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackTransferService {
    private final TrackBusinessService businessService;
    private final UserTrackLogService userTrackLogService;
    private final UserTrackOrderService userTrackOrderService;
    private final UserGenreService userGenreService;
    private final UserService userService;

    @Transactional
    public MusicDto.Track previous(final SessionUser sessionUser, final List<Integer> genres) {
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var userTrackOrder = userTrackOrderService.find(sessionUser.getUserId());
        final var user = userService.findById(sessionUser.getUserId());

        return businessService.previous(allLogs, userTrackOrder, user, genres);
    }

    @Transactional
    public MusicDto.Track following(final SessionUser sessionUser, final List<Integer> genres) {
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var userTrackOrder = userTrackOrderService.find(sessionUser.getUserId());
        final var user = userService.findById(sessionUser.getUserId());

        return businessService.following(allLogs, userTrackOrder, user, genres);
    }
}
