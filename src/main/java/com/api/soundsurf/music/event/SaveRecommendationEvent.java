package com.api.soundsurf.music.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Getter
public class SaveRecommendationEvent extends ApplicationEvent {
    private final Track[] data;
    private final Long userId;
    private final Long lastOrder;
    private final boolean isPrev;

    public SaveRecommendationEvent(Object source, final Long lastOrder, final Track[] data, final Long userId, final boolean isPrev) {
        super(source);
        this.lastOrder = lastOrder;
        this.data = data;
        this.userId = userId;
        this.isPrev = isPrev;
    }

}