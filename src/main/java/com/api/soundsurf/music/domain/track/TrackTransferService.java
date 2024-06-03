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

@Service
@RequiredArgsConstructor
public class TrackTransferService {
    private final TrackBusinessService businessService;
    private final UserTrackLogService userTrackLogService;
    private final UserTrackOrderService userTrackOrderService;
    private final UserGenreService userGenreService;
    private final UserService userService;

    @Transactional
    public MusicDto.Track previous(final SessionUser sessionUser) {
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var userTrackOrder = userTrackOrderService.find(sessionUser.getUserId());

        final var previousLog = businessService.previous(allLogs, userTrackOrder);

        final var song = new MusicDto.Common.Song(previousLog);
        return new MusicDto.Track(song, userTrackOrder.getOrder(), allLogs.get(allLogs.size() - 1).getOrder());
    }

    @Transactional
    public MusicDto.Track following(final SessionUser sessionUser) {
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var userTrackOrder = userTrackOrderService.find(sessionUser.getUserId());
        final var user = userService.findById(sessionUser.getUserId());
        final var userGenres = userGenreService.getAllByUser(user);

        final var lastLog = allLogs.get(allLogs.size() - 1);

        if (lastLog.getOrder() <= userTrackOrder.getOrder()) {
            final var nowTrack = businessService.getNewTrack(sessionUser.getUserId(), userGenres);
            return new MusicDto.Track(nowTrack, userTrackOrder.getOrder(), allLogs.get(allLogs.size() - 1).getOrder());
        }

        final var previousLog = businessService.following(allLogs, userTrackOrder);

        final var song = new MusicDto.Common.Song(previousLog);
        return new MusicDto.Track(song, userTrackOrder.getOrder(), allLogs.get(allLogs.size() - 1).getOrder());
    }

}
