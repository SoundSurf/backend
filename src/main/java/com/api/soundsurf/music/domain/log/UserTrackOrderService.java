package com.api.soundsurf.music.domain.log;

import com.api.soundsurf.music.entity.UserTrackOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTrackOrderService {
    private final UserTrackOrderRepository repository;

    public UserTrackOrder find(final Long userId) {
        return repository.findByUserId(userId);
    }

    public void setNewOrder(final UserTrackOrder order, final Long newOrder) {
        order.updateOrder(newOrder);
        repository.save(order);
    }


}
