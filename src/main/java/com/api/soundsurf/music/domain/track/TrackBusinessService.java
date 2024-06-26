package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.GeneralMusicBusinessService;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
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
    private final UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;
    private final GeneralMusicBusinessService generalMusicBusinessService;


    public MusicDto.Track previous(final List<UserTrackLog> userTrackLogs, final UserTrackOrder order, final User user, final List<Integer> genres) {
        final var response = new MusicDto.Track();
//        Long nowOrder = order.getOrder();

        for (var log : userTrackLogs) {
            if (Objects.equals(log.getOrder(), order.getOrder())) {
                //무조건 실행된다.
                response.setNextSong(new MusicDto.Common.Song(log));

                break;
            }
        }

        boolean nowSongExist = false;
        boolean prevSongExist = false;
        Long nowSongOrder = null;
        for (int i = userTrackLogs.size() - 1; i >= 0; i--) {
            var log = userTrackLogs.get(i);

            if (!nowSongExist && log.getOrder() < order.getOrder()) {
                final var nowSong = new MusicDto.Common.Song(log);
                createPrevSongTrackLog(userTrackLogs, user, order, nowSong);
                response.setNowSong(nowSong);
                nowSongExist = true;
                nowSongOrder = log.getOrder();

                continue;
            }

            if (nowSongExist && log.getOrder() < order.getOrder() - 1L) {
                response.setPrevSong(new MusicDto.Common.Song(log));
                prevSongExist = true;

                break;
            }
        }

        if (!nowSongExist) {
            final var nowSongByPrevRecommendationLogs = userRecommendationMusicBusinessService.getByOrder(user.getId(), order.getOrder() - 1L);
            MusicDto.Common.Song nowSong;

            if (nowSongByPrevRecommendationLogs == null) {
                nowSong = getNewTrack(user, genres, true, true);
            } else {
                nowSong = new MusicDto.Common.Song(nowSongByPrevRecommendationLogs);
            }

            createPrevSongTrackLog(userTrackLogs, user, order, nowSong);
            response.setNowSong(nowSong);

        }


        if (!prevSongExist) {
            final var prevSongLog = userRecommendationMusicBusinessService.getByOrder(user.getId(), order.getOrder() - 2L);
            MusicDto.Common.Song prevSong;

            if (prevSongLog == null) {
                prevSong = getNewTrack(user, genres, true, true);
            } else {
                prevSong = new MusicDto.Common.Song(prevSongLog);
            }
            response.setPrevSong(prevSong);
        }

        userTrackOrderService.minusOrder(order);
        return response;
    }

    private void createPrevSongTrackLog(final List<UserTrackLog> userTrackLogs, final User user, final UserTrackOrder userTrackOrder, final MusicDto.Common.Song song) {
        if (!userTrackLogs.stream().map(UserTrackLog::getOrder).toList().contains(userTrackOrder.getOrder() - 1L)) {
            userTrackLogService.createPrevSongLog(userTrackOrder.getOrder(), song, user);
        }
    }

    private void createNextSongTrackLog(final List<UserTrackLog> userTrackLogs, final User user, final UserTrackOrder userTrackOrder, final MusicDto.Common.Song song) {
        if (!userTrackLogs.stream().map(UserTrackLog::getOrder).toList().contains(userTrackOrder.getOrder() + 1L)) {
            userTrackLogService.createNextSongLog(userTrackOrder.getOrder(), song, user);
        }
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

        final var nowSongByPrevRecommendationLogs = userRecommendationMusicBusinessService.get(user.getId());
        final var nowSong = generalMusicBusinessService.getNextTrackWhenFollowing(logs, nowSongByPrevRecommendationLogs, order, user, genres);
        createNextSongTrackLog(logs, user, order, nowSong);
        response.setNowSong(nowSong);

        userTrackOrderService.plusOrder(order);

        final var nextSongByPrevRecommendationLogs = userRecommendationMusicBusinessService.get(user.getId());
        final var nextSong = generalMusicBusinessService.getNextTrackWhenFollowing(logs, nextSongByPrevRecommendationLogs, order, user, genres);
        response.setNextSong(nextSong);

        return response;
    }

    public MusicDto.Common.Song getNewTrack(final User user, final List<Integer> requestGenres, final boolean needMoreTracks, final boolean isPrev) {
        final var userGenres = user.getUserGenres();

        if (requestGenres == null || !requestGenres.isEmpty()) {
            return generalMusicBusinessService.find(requestGenres, user, needMoreTracks, isPrev);
        }

        if (!userGenres.isEmpty()) {
            final var userGenreList = user.getUserGenres().stream().map(UserGenre::getGenreId).toList();
            return generalMusicBusinessService.find(userGenreList, user, needMoreTracks, isPrev);
        }

        final var randGenres = GenreType.getRandomGenres(5).stream().map(GenreType::getIndex).toList();
        return generalMusicBusinessService.find(randGenres, user, needMoreTracks, isPrev);
    }
}
