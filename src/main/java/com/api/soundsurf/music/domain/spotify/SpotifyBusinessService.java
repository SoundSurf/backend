package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.domain.GeneralMusicBusinessService;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import com.api.soundsurf.music.entity.UserTrackLog;
import com.api.soundsurf.music.entity.UserTrackOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SpotifyBusinessService {
    private final UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;
    private final UserRecommendationMusicService userRecommendationMusicService;
    private final UserTrackLogService userTrackLogService;
    private final UserTrackOrderService userTrackOrderService;
    private final GeneralMusicBusinessService generalMusicBusinessService;

    public MusicDto.Track recommend(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final User user, final MusicDto.Track response, final UserTrackOrder userTrackOrder, final List<UserTrackLog> userTrackLogs) {
        if (user.isFirstDrive()) {
            final var song = generalMusicBusinessService.find(prevRecommendedMusics, genres, user, false, false);
            response.setNowSong(song);
            final var newRecommendedMusic = userRecommendationMusicBusinessService.getByOrder(user.getId(), 2L);
            response.setNextSong(new MusicDto.Common.Song(newRecommendedMusic));
            final var prevSong = generalMusicBusinessService.find(userRecommendationMusicService.get(user.getId()), genres, user, true, true);
            response.setPrevSong(prevSong);

            userTrackLogService.createLog(1L, song, user);


        } else {
            for (var log : userTrackLogs) {
                if (Objects.equals(log.getOrder(), userTrackOrder.getOrder())) {
                    //무조건 실행됨
                    response.setPrevSong(new MusicDto.Common.Song(log));

                    break;
                }
            }

            final var nowSong = generalMusicBusinessService.getNextTrackWhenFollowing(userTrackLogs, prevRecommendedMusics, userTrackOrder, user, genres);
            response.setNowSong(nowSong);
            if (!userTrackLogs.stream().map(UserTrackLog::getOrder).toList().contains(userTrackOrder.getOrder() + 1L)) {
                userTrackLogService.createNextSongLog(userTrackOrder.getOrder(), nowSong, user);
            }

            userTrackOrderService.plusOrder(userTrackOrder);

            final var nextSongByPrevRecommendationLogs = userRecommendationMusicBusinessService.get(user.getId());
            final var nextSong = generalMusicBusinessService.getNextTrackWhenFollowing(userTrackLogs, nextSongByPrevRecommendationLogs, userTrackOrder, user, genres);
            response.setNextSong(nextSong);
        }

        return response;
    }

}
