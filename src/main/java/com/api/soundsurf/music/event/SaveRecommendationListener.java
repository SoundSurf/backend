package com.api.soundsurf.music.event;

import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicBusinessService;
import com.api.soundsurf.music.domain.spotify.SpotifyBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SaveRecommendationListener {
    @Autowired
    private UserRecommendationMusicBusinessService userRecommendationMusicBusinessService;

    @EventListener
    @Async
    public void onApplicationEvent(SaveRecommendationEvent event) {
        userRecommendationMusicBusinessService.save(event.getData(), event.getLastOrder(), event.getUserId());
//        System.out.println("Handling event with data: " + Arrays.toString(event.getData()));
    }
}