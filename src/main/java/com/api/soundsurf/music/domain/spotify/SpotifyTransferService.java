package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.dto.SessionUser;
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
    private final UserTrackLogService userTrackLogService;

    @Transactional
    public MusicDto.Track playWithId(final SessionUser sessionUser, final MusicDto.Play.Request req) {
        final var track = businessService.findTrack(req.trackId());
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var logMap = businessService.createLogMap(allLogs);
        final var nowPlayTrackLog = logMap.get(req.trackId());

        userTrackLogService.playAndUpdate(nowPlayTrackLog.getLog());

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
