package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.log.UserTrackLogService;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
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


    public MusicDto.Track  previous(final List<UserTrackLog> logs, final UserTrackOrder order, final User user) {
        final var response = new MusicDto.Track();


        for (var log : logs) {
            if (Objects.equals(log.getOrder(), order.getOrder())) {
                response.setNextSong(new MusicDto.Common.Song(log));

                userTrackOrderService.setNewOrder(order, log.getOrder() - 1L);
                break;
            }
        }

        boolean nowSongExist = false;
        boolean prevSongExist = false;
        Long nowSongOrder = null;
        for (var log : logs) {
            if (!nowSongExist && log.getOrder() <= order.getOrder() - 1L) {
                response.setNowSong(new MusicDto.Common.Song(log));
                userTrackOrderService.setNewOrder(order, log.getOrder() - 1L);
                nowSongExist = true;
                nowSongOrder = log.getOrder();

                continue;
            }

            if (nowSongExist && log.getOrder() <= order.getOrder() - 1L) {
                response.setPrevSong(new MusicDto.Common.Song(log));
                prevSongExist = true;

                break;
            }
        }

        if (!nowSongExist) {
            final var nowSong = getNewTrack(user, user.getUserGenres());
            userTrackOrderService.setNewOrder(order, logs.get(0).getOrder() - 1L);
            userTrackLogService.createPrevSongLog(order.getOrder(), nowSong, user);
            response.setNowSong(nowSong);
        }

        if (!prevSongExist) {
            final var prevSong = getNewTrack(user, user.getUserGenres());
            userTrackLogService.createPrevSongLog(nowSongOrder, prevSong, user);
            response.setPrevSong(prevSong);
        }

        return response;
    }

    public MusicDto.Track following(final List<UserTrackLog> logs, final UserTrackOrder order, final User user) {
        final var response = new MusicDto.Track();


        for (var log : logs) {
            if (Objects.equals(log.getOrder(), order.getOrder())) {
                final var now = getNewTrack(user, user.getUserGenres());
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
        final var newSong = getNewTrack(user, user.getUserGenres());
        userTrackLogService.createNextSongLog(logs.get(logs.size() - 1).getOrder(), newSong, user);
        response.setNextSong(newSong);

        return response;
    }

    public MusicDto.Common.Song getNewTrack(final User user, final List<UserGenre> userGenres) {
        final var genres = userGenres.stream()
                .map(userGenre -> GenreType.valueOf(userGenre.getGenre().getName()))
                .toList();

        return spotifyBusinessService.find(genres, user);
    }
}
