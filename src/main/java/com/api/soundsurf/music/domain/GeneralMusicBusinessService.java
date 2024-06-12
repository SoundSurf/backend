package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.domain.spotify.DriveService;
import com.api.soundsurf.music.domain.spotify.SpotifyBusinessService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import com.api.soundsurf.music.entity.UserTrackLog;
import com.api.soundsurf.music.entity.UserTrackOrder;
import com.api.soundsurf.music.event.GetRecommendationsAndSaveRecommendationEvent;
import com.api.soundsurf.music.event.SaveRecommendationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class GeneralMusicBusinessService {
    @Autowired
    private ApplicationEventPublisher publisher;
    private final UserRecommendationMusicService userRecommendationMusicService;
    private final UserTrackOrderService userTrackOrderService;
    private final DriveService driveService;
    private final UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;


    public MusicDto.Common.Song getNewTrack(final User user, final List<Integer> requestGenres, final boolean needMoreTracks, final boolean isPrev) {
        final var userGenres = user.getUserGenres();

        if (requestGenres == null || !requestGenres.isEmpty()) {
            return find(requestGenres, user, needMoreTracks, isPrev);
        }

        if (!userGenres.isEmpty()) {
            final var userGenreList = user.getUserGenres().stream().map(UserGenre::getGenreId).toList();
            return find(userGenreList, user, needMoreTracks, isPrev);
        }

        final var randGenres = GenreType.getRandomGenres(5).stream().map(GenreType::getIndex).toList();
        return find(randGenres, user, needMoreTracks, isPrev);
    }

    public MusicDto.Common.Song getNextTrackWhenFollowing(final List<UserTrackLog> userTrackLogs, final List<UserRecommendationMusic> prevRecommendationMusics, final UserTrackOrder order, final User user, final List<Integer> genres) {
        boolean find = false;

        final var userTrackLogNextTrack = findAmongUserTrackLogs(userTrackLogs, order.getOrder() + 1L);
        if (userTrackLogNextTrack != null) {
            return userTrackLogNextTrack;
        }

        final var recommendNextTrack = findAmongPreRecommends(prevRecommendationMusics, order.getOrder() + 1L);
        if (recommendNextTrack != null) {
            return recommendNextTrack;
        }

        return getNewTrack(user, genres, true, false);
    }

    public MusicDto.Common.Song find(final List<Integer> genres, final User user, final boolean needMoreTracks, final boolean isPrev) {
        final var prevRecommendedMusics = userRecommendationMusicService.get(user.getId());

        return find(prevRecommendedMusics, genres, user, needMoreTracks, isPrev);
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
            return returnAndGetRecommendationAndSave(prevRecommendedMusics, genres, user.getId(), isPrev);
        }

        return returnFirstOrderRecommendation(prevRecommendedMusics, user.getId());
    }


    private MusicDto.Common.Song findAmongUserTrackLogs(final List<UserTrackLog> userTrackLogs,
                                                        final Long targetOrder) {
        boolean find = false;

        for (var userTrackLog : userTrackLogs) {
            if (Objects.equals(userTrackLog.getOrder(), targetOrder)) {
                return new MusicDto.Common.Song(userTrackLog);
            }

            if (!find && userTrackLog.getOrder() > targetOrder) {
                return new MusicDto.Common.Song(userTrackLog);
            }
        }
        return null;
    }

    private MusicDto.Common.Song findAmongPreRecommends(final List<UserRecommendationMusic> prevRecommendationMusics, final Long trackOrder) {
        boolean find = false;

        for (var prevRecommendationMusic : prevRecommendationMusics) {
            if (Objects.equals(prevRecommendationMusic.getOrder(), trackOrder)) {
                return new MusicDto.Common.Song(prevRecommendationMusic);
            }

            if (!find && prevRecommendationMusic.getOrder() > trackOrder) {
                return new MusicDto.Common.Song(prevRecommendationMusic);
            }
        }
        return null;
    }

    private MusicDto.Common.Song getRecommendationAndSave(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final Long userId, final boolean isPrev) {
        final var recommendations = driveService.recommendation(genres);


        publisher.publishEvent(new SaveRecommendationEvent(this, getLastOrder(prevRecommendedMusics, isPrev), recommendations, userId, isPrev));

        return new MusicDto.Common.Song(recommendations[0]);
    }

    private MusicDto.Common.Song returnAndGetRecommendationAndSave(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final Long userId, final boolean isPrev) {
        final var lastOrder = prevRecommendedMusics.get(prevRecommendedMusics.size() - 1).getOrder();
        final var nowRecommendedMusic = prevRecommendedMusics.get(0);
        publisher.publishEvent(new GetRecommendationsAndSaveRecommendationEvent(this, genres, userId, lastOrder, isPrev));
        userRecommendationMusicBusinessService.listenAndDelete(userId, nowRecommendedMusic.getId());

        return new MusicDto.Common.Song(nowRecommendedMusic);
    }

    private MusicDto.Common.Song returnFirstOrderRecommendation(final List<UserRecommendationMusic> prevRecommendedMusics, final Long userId) {
        userRecommendationMusicBusinessService.listenAndDelete(userId, prevRecommendedMusics.get(0).getId());

        return new MusicDto.Common.Song(prevRecommendedMusics.get(0));
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
}

