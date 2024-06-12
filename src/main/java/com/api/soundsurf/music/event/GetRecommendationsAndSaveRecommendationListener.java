package com.api.soundsurf.music.event;

import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import com.api.soundsurf.music.domain.spotify.DriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GetRecommendationsAndSaveRecommendationListener {
    @Autowired
    private DriveService driveService;
    @Autowired
    private UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;


    @EventListener
    @Async
    public void onApplicationEvent(GetRecommendationsAndSaveRecommendationEvent event) {
        final var recommendations = driveService.recommendation(event.getGenres());
        userRecommendationMusicBusinessService.save(recommendations, event.getLastOrder(), event.getUserId(), event.isPrev());
    }
}