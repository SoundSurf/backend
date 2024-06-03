package com.api.soundsurf.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_track_orders")
public class UserTrackOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private Long order;

    @Column(nullable = false)
    private boolean first = false;

    @Column(nullable = false)
    private boolean last = false;

    public UserTrackOrder(final Long userId, final Long order){
        this.userId = userId;
        this.order = order;
    }

    public void updateOrder(final Long order) {
        this.order = order;
    }

    public void lastOrder(final Long newOrder) {
        this.order = newOrder;
        this.last = true;
    }

    public void firstOrder() {
        this.order = 0L;
        this.first = true;
    }
}
