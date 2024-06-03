package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import com.api.soundsurf.music.entity.UserTrackLog;
import com.api.soundsurf.music.event.GetRecommendationsAndSaveRecommendationEvent;
import com.api.soundsurf.music.event.SaveRecommendationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class SpotifyBusinessService {
    private final DriveService driveService;
    @Autowired
    private ApplicationEventPublisher publisher;
    private final UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;
    private final UserRecommendationMusicService userRecommendationMusicService;

    public MusicDto.Common.Song find(final List<GenreType> genres, final Long userId) {
        final var prevRecommendedMusics = userRecommendationMusicService.get(userId);

        return find(prevRecommendedMusics, genres, userId);
    }

    public MusicDto.Common.Song find(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final Long userId) {
        if (prevRecommendedMusics == null || prevRecommendedMusics.isEmpty()) {
            return getRecommendationAndSave(genres, userId);
        } else if (prevRecommendedMusics.size() <= 3) {
            return returnAndGetRecommendationAndSave(prevRecommendedMusics, genres, userId);
        }

        return returnFirstOrderRecommendation(prevRecommendedMusics, userId);
    }

    public int findPrevLogIndexInAllLogs(List<UserTrackLog> allLogs, Long logId) {
        var i = allLogs.size() - 1;
        for (; i >= 0; i--) {
            if (Objects.equals(allLogs.get(i).getId(), logId)) {
                return i;
            }
        }

        throw new ApiException("log list error");
    }

    public Map<String, LogWithIndex> createLogMap(final List<UserTrackLog> allLogs) {
        final var logMap = new HashMap<String, LogWithIndex>();
        var cnt = new AtomicReference<Long>(1L);
        for (UserTrackLog log : allLogs) {
            logMap.put(log.getTrackId(), new LogWithIndex(log, cnt));
            cnt.set(cnt.get() + 1L);
        }

        return logMap;
    }

    public Track findTrack(final String trackId) {
        return driveService.find(trackId);
    }

    public HashMap<Long, UserTrackLog> getPreviousLog(Long logId, List<UserTrackLog> allLogs) {
        var find = false;
        var cnt = 1L;
        final var map = new HashMap<Long, UserTrackLog>();
        for (var log : allLogs) {
            if (find) {
                map.put(cnt, log);
            }
            if (Objects.equals(log.getId(), logId)) {
                find = true;
                cnt++;
            }
        }

        return map;
    }

    private MusicDto.Common.Song getRecommendationAndSave(final List<GenreType> genres, final Long userId) {
        final var recommendations = driveService.recommendation(genres);
        publisher.publishEvent(new SaveRecommendationEvent(this, 0L, recommendations, userId));

        return new MusicDto.Common.Song(recommendations[0]);
    }

    private MusicDto.Common.Song returnAndGetRecommendationAndSave(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final Long userId) {
        final var lastOrder = prevRecommendedMusics.get(prevRecommendedMusics.size() - 1).getOrder();
        final var nowRecommendedMusic = prevRecommendedMusics.get(0);
        publisher.publishEvent(new GetRecommendationsAndSaveRecommendationEvent(this, genres, userId, lastOrder));
        userRecommendationMusicBusinessService.listenAndDelete(userId, nowRecommendedMusic.getId());

        return new MusicDto.Common.Song(nowRecommendedMusic);
    }

    private MusicDto.Common.Song returnFirstOrderRecommendation(final List<UserRecommendationMusic> prevRecommendedMusics, final Long userId) {
        userRecommendationMusicBusinessService.listenAndDelete(userId, prevRecommendedMusics.get(0).getId());

        return new MusicDto.Common.Song(prevRecommendedMusics.get(0));
    }

    @Getter
    @AllArgsConstructor
    static class LogWithIndex {
        private UserTrackLog log;
        private AtomicReference<Long> index;
    }
}
