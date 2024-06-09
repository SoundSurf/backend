package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
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

    public MusicDto.Common.Song find(final List<UserRecommendationMusic> prevRecommendedMusics, final List<Integer> genres, final Long userId) {
        if (prevRecommendedMusics == null || prevRecommendedMusics.isEmpty()) {
            return getRecommendationAndSave(genres, userId);
        } else if (prevRecommendedMusics.size() <= 3) {
            return returnAndGetRecommendationAndSave(prevRecommendedMusics, genres, userId);
        }

        return returnFirstOrderRecommendation(prevRecommendedMusics, userId);
    }

    private MusicDto.Common.Song getRecommendationAndSave(final List<Integer> genres, final Long userId) {
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
