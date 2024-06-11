package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.constant.GenreType;
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


    public MusicDto.Track previous(final List<UserTrackLog> logs, final UserTrackOrder order, final User user, final List<Integer> genres) {
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
                final var nowSong = new MusicDto.Common.Song(log);
                userTrackLogService.createPrevSongLog(order.getOrder(), nowSong, user);
                response.setNowSong(nowSong);
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
                nowSong = getNewTrack(user, genres, true, true);
            } else {
                nowSong = new MusicDto.Common.Song(nowSongByPrevRecommendationLogs);
            }

            nowSongOrder = nowOrder - 1L;
            userTrackLogService.createPrevSongLog(order.getOrder(), nowSong, user);
            response.setNowSong(nowSong);

        }


        if (!prevSongExist) {
            final var prevSongLog = userRecommendationMusicService.getByOrder(user.getId(), order.getOrder() - 1L);
            MusicDto.Common.Song prevSong;

            if (prevSongLog == null) {
                prevSong = getNewTrack(user, genres, true, true);
            } else {
                prevSong = new MusicDto.Common.Song(prevSongLog);
            }
            response.setPrevSong(prevSong);
        }

        return response;
    }

    public MusicDto.Track following(final List<UserTrackLog> logs, final UserTrackOrder order, final User user, final List<Integer> genres) {
        final var response = new MusicDto.Track();


        for (var log : logs) {
            if (Objects.equals(log.getOrder(), order.getOrder())) {
                //무조건 실행됨
                response.setPrevSong(new MusicDto.Common.Song(log));

                break;
            }
        }

        userTrackOrderService.setNewOrder(order, order.getOrder() + 1L);
        final var nowSongByPrevRecommendationLogs = userRecommendationMusicService.getByOrder(user.getId(), order.getOrder());
        MusicDto.Common.Song nowSong;
        if (nowSongByPrevRecommendationLogs == null) {
            nowSong = getNewTrack(user, genres, true, false);
        } else {
            nowSong = new MusicDto.Common.Song(nowSongByPrevRecommendationLogs);
        }
        userTrackLogService.createPrevSongLog(order.getOrder(), nowSong, user);
        response.setNowSong(nowSong);

        final var prevSongByPrevRecommendationLogs = userRecommendationMusicService.getByOrder(user.getId(), order.getOrder()+1L);
        MusicDto.Common.Song nextSong;
        if (prevSongByPrevRecommendationLogs == null) {
            nextSong = getNewTrack(user, genres, true, false);
        } else {
            nextSong = new MusicDto.Common.Song(prevSongByPrevRecommendationLogs);
        }
        response.setNextSong(nextSong);

        return response;
    }

    public MusicDto.Common.Song getNewTrack(final User user, List<Integer> genres, final boolean needMoreTracks, final boolean isPrev) {
        if (genres.isEmpty()) {
            final var userGenres = user.getUserGenres();
            if(userGenres.isEmpty()) {
                genres = GenreType.getRandomGenres(5).stream().map(GenreType::getIndex).toList();
            } else genres = userGenres.stream().map(UserGenre::getGenreId).toList();
        }

        return spotifyBusinessService.find(genres, user, needMoreTracks, isPrev);
    }
}
