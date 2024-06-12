package com.api.soundsurf.music.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class GetRecommendationsAndSaveRecommendationEvent extends ApplicationEvent {
    private final List<Integer> genres;
    private final Long userId;
    private final Long lastOrder;
    private final boolean isPrev;

    public GetRecommendationsAndSaveRecommendationEvent(Object source, final List<Integer>  genres, final Long userId, final Long lastOrder, final boolean isPrev) {
        super(source);
        this.genres = genres;
        this.userId = userId;
        this.lastOrder = lastOrder;
        this.isPrev = isPrev;
    }

}