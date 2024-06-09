package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class SpotifyBusinessService {
    private final DriveService driveService;
    @Autowired
    private ApplicationEventPublisher publisher;
    private final UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;
    private final UserRecommendationMusicService userRecommendationMusicService;
    private final UserTrackLogService userTrackLogService;
    private final UserTrackOrderService userTrackOrderService;

    public MusicDto.Common.Song find(final List<GenreType> genres, final User user) {
        final var prevRecommendedMusics = userRecommendationMusicService.get(user.getId());

        return find(prevRecommendedMusics, genres, user);
    }

    public MusicDto.Common.Song findAndMakeLog(final List<UserRecommendationMusic> prevRecommendedMusics, final List<GenreType> genres, final User user) {
        final var song = find(prevRecommendedMusics, genres, user);
        userTrackLogService.createNextSongLog(prevRecommendedMusics, song, user);

        return song;
    }

    public MusicDto.Common.Song find(final List<UserRecommendationMusic> prevRecommendedMusics, final List<GenreType> genres, final User user) {
        if (user.isFirstDrive()) {
            user.setFirstDrive(false);
            userTrackOrderService.createNew(user.getId());
        }

        if (prevRecommendedMusics == null || prevRecommendedMusics.size() == 0) {
            return getRecommendationAndSave(genres, user.getId());

        } else if (prevRecommendedMusics.size() <= 3) {
            return returnAndGetRecommendationAndSave(prevRecommendedMusics, genres, user.getId());
        }

        return returnFirstOrderRecommendation(prevRecommendedMusics, user.getId());
    }

    public Track findTrack(final String trackId) {
        return driveService.find(trackId);
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
}
