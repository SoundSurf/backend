package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.api.exception.ApiException;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.log.UserTrackOrderService;
import com.api.soundsurf.music.domain.spotify.SpotifyBusinessService;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserTrackLog;
import com.api.soundsurf.music.entity.UserTrackOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackBusinessService {
    private final UserTrackOrderService userTrackOrderService;
    private final SpotifyBusinessService spotifyBusinessService;


    public UserTrackLog previous(final List<UserTrackLog> logs, final UserTrackOrder order) {
        if (logs.size() == 0) {
            throw new ApiException("안돼 돌아가");
        }

        final var firstLog = logs.get(0);

        for (int i = logs.size() - 1; i >= 0; i--) {
            if (logs.get(i).getOrder() < order.getOrder()) {
                userTrackOrderService.setNewOrder(order, logs.get(i).getOrder() - 1L);
                return logs.get(i);
            }
        }

        userTrackOrderService.setNewOrder(order, 0L);
        return firstLog;
    }

    public UserTrackLog following(final List<UserTrackLog> logs, final UserTrackOrder order) {
        final var lastLog = logs.get(logs.size() - 1);

        for (var log : logs) {
            if (log.getOrder() > order.getOrder()) {
                userTrackOrderService.setNewOrder(order, log.getOrder() + 1L);
                return log;
            }
        }

        userTrackOrderService.setNewOrder(order, logs.get(logs.size() - 1).getOrder() + 1L);
        return lastLog;
    }

    public MusicDto.Common.Song getNewTrack(final Long userId, final List<UserGenre> userGenres) {
        final var genres = userGenres.stream()
                .map(userGenre -> GenreType.valueOf(userGenre.getGenre().getName()))
                .toList();

        return spotifyBusinessService.find(genres, userId);
    }
}
