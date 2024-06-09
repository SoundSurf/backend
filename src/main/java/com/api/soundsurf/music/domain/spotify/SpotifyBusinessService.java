package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import com.api.soundsurf.music.event.GetRecommendationsAndSaveRecommendationEvent;
import com.api.soundsurf.music.event.SaveRecommendationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public MusicDto.Common.Song find(final List<Integer> genres, final User user, final boolean needMoreTracks, final boolean isPrev) {
        final var prevRecommendedMusics = userRecommendationMusicService.get(user.getId());

        return find(prevRecommendedMusics, genres, user, needMoreTracks, isPrev);
    }

    public MusicDto.Track findAndMakeLog(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final User user, final MusicDto.Track response) {
        final var song = find(prevRecommendedMusics, genres, user, false, false);
        response.setNowSong(song);
        final var nextSong = find(userRecommendationMusicService.get(user.getId()), genres, user, false, false);
        response.setNextSong(nextSong);
        final var prevSong = find(userRecommendationMusicService.get(user.getId()), genres, user, false, false);
        response.setPrevSong(prevSong);

        if (prevRecommendedMusics == null || prevRecommendedMusics.size() == 0) {
            userTrackLogService.createNextSongLog(0L, song, user);

        } else {
            userTrackLogService.createNextSongLog(prevRecommendedMusics, song, user);
        }

        return response;
    }

    public MusicDto.Common.Song find(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final User user, final boolean needMoreTracks, final boolean isPrev) {
        if (user.isFirstDrive()) {
            user.setFirstDrive(false);
            userTrackOrderService.createNew(user.getId());
        }

        if (needMoreTracks) {
            return getRecommendationAndSave(prevRecommendedMusics, genres, user.getId(), isPrev);
        }

        if (prevRecommendedMusics == null || prevRecommendedMusics.size() == 0) {
            return getRecommendationAndSave(prevRecommendedMusics, genres, user.getId(), isPrev);

        } else if (prevRecommendedMusics.size() <= 3) {
            return returnAndGetRecommendationAndSave(prevRecommendedMusics, genres, user.getId());
        }

        return returnFirstOrderRecommendation(prevRecommendedMusics, user.getId());
    }

    private MusicDto.Common.Song getRecommendationAndSave(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final Long userId, final boolean isPrev) {
        final var recommendations = driveService.recommendation(genres);


        publisher.publishEvent(new SaveRecommendationEvent(this, getLastOrder(prevRecommendedMusics, isPrev), recommendations, userId, isPrev));

        return new MusicDto.Common.Song(recommendations[0]);
    }

    private Long getLastOrder(final List<UserRecommendationMusic> prevRecommendedMusics, final boolean isPrev) {
        if (prevRecommendedMusics == null || prevRecommendedMusics.size() == 0) {
            return 0L;
        }

        if (isPrev) {
            return prevRecommendedMusics.get(0).getOrder();
        }

        return prevRecommendedMusics.get(prevRecommendedMusics.size() - 1).getOrder();
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
