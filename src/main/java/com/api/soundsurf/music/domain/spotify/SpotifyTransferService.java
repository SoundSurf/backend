package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.iam.exception.UserGenreCountException;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.dto.MusicDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyTransferService {
    private final UserRecommendationMusicService userRecommendationMusicService;
    private final SpotifyBusinessService businessService;
    private final UserBusinessService userBusinessService;
    private final UserTrackLogService userTrackLogService;

    @Transactional

    public MusicDto.Track playWithId(final SessionUser sessionUser, final MusicDto.Play.Request req) {
        final var user = userBusinessService.getUser(sessionUser.getUserId());
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));

        final var track = businessService.findTrack(req.trackId());
        final var logMap = businessService.createUserTrackLogMap(allLogs, user, req.trackId());
        final var nowPlayTrackLog = logMap.get(req.trackId());

        final var song = new MusicDto.Common.Song(track);
        return new MusicDto.Track(song, nowPlayTrackLog.getIndex(), logMap.size());
    }

    @Transactional
    public MusicDto.Common.Song recommend(final List<Integer> genres, final SessionUser sessionUser) {
        if (genres.size() > 3)
            throw new UserGenreCountException();

        final var userId = sessionUser.getUserId();
        final var prevRecommendedMusics = userRecommendationMusicService.get(userId);

        return businessService.find(prevRecommendedMusics, genres, sessionUser.getUserId());
    }

}
