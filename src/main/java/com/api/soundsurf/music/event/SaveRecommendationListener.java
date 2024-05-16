package com.api.soundsurf.music.event;

import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SaveRecommendationListener {
    @Autowired
    private UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;

    @EventListener
    @Async
    public void onApplicationEvent(SaveRecommendationEvent event) {
        userRecommendationMusicBusinessService.firstRecommendAndSave(event.getData(), event.getLastOrder(), event.getUserId());
    }
}