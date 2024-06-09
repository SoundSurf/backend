package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.domain.spotify.SpotifyBusinessService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserTrackLog;
import com.api.soundsurf.music.entity.UserTrackOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TrackBusinessService {
    private final UserTrackOrderService userTrackOrderService;
    private final UserTrackLogService userTrackLogService;
    private final SpotifyBusinessService spotifyBusinessService;
    private final UserRecommendationMusicService userRecommendationMusicService;


    public MusicDto.Track previous(final List<UserTrackLog> logs, final UserTrackOrder order, final User user) {
        final var response = new MusicDto.Track();
        Long nowOrder = order.getOrder();

        for (var log : logs) {
            if (Objects.equals(log.getOrder(), order.getOrder())) {
                //무조건 실행된다.
                response.setNextSong(new MusicDto.Common.Song(log));
                userTrackOrderService.setNewOrder(order, --nowOrder);

                break;
            }
        }

        boolean nowSongExist = false;
        boolean prevSongExist = false;
        Long nowSongOrder = null;
        for (var log : logs) {
            if (!nowSongExist && log.getOrder() <= nowOrder) {
                response.setNowSong(new MusicDto.Common.Song(log));
                nowSongExist = true;
                nowSongOrder = log.getOrder();
                nowOrder = log.getOrder();

                continue;
            }

            if (nowSongExist && log.getOrder() <= nowOrder - 1L) {
                response.setPrevSong(new MusicDto.Common.Song(log));
                prevSongExist = true;

                break;
            }
        }

        if (!nowSongExist) {
            final var nowSongByPrevRecommendationLogs = userRecommendationMusicService.getByOrder(user.getId(), order.getOrder());
            MusicDto.Common.Song nowSong;

            if (nowSongByPrevRecommendationLogs == null) {
                nowSong = getNewTrack(user, user.getUserGenres(), true, true);
            } else {
                nowSong = new MusicDto.Common.Song(nowSongByPrevRecommendationLogs);
            }

            nowSongOrder = nowOrder - 1L;
            userTrackLogService.createPrevSongLog(order.getOrder(), nowSong, user);
            response.setNowSong(nowSong);

        }


        if (!prevSongExist) {
            final var prevSongLog = userRecommendationMusicService.getByOrder(user.getId(), order.getOrder() - 1L);
            final var prevSong = new MusicDto.Common.Song(prevSongLog);
            response.setPrevSong(prevSong);
        }

        return response;
    }

    public MusicDto.Track following(final List<UserTrackLog> logs, final UserTrackOrder order, final User user) {
        final var response = new MusicDto.Track();


        for (var log : logs) {
            if (Objects.equals(log.getOrder(), order.getOrder())) {
                final var now = getNewTrack(user, user.getUserGenres(), false, false);
                response.setPrevSong(new MusicDto.Common.Song(log));
                response.setNowSong(now);

                userTrackOrderService.setNewOrder(order, log.getOrder() + 1L);
                break;
            }
        }

        for (var log : logs) {
            if (log.getOrder() >= order.getOrder() + 1L) {
                response.setNextSong(new MusicDto.Common.Song(log));
                return response;
            }
        }
        final var newSong = getNewTrack(user, user.getUserGenres(), true, false);
        userTrackLogService.createNextSongLog(logs.get(logs.size() - 1).getOrder(), newSong, user);
        response.setNextSong(newSong);

        return response;
    }

    public MusicDto.Common.Song getNewTrack(final User user, final List<UserGenre> userGenres, final boolean needMoreTracks, final boolean isPrev) {
        final var genres = userGenres.stream()
                .map(UserGenre::getGenreId)
                .toList();

        return spotifyBusinessService.find(genres, user, needMoreTracks, isPrev);
    }
}
