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

    public void plusOrder( final  UserTrackOrder order){
        setNewOrder(order, order.getOrder() + 1L);
        repository.save(order);
    }

    public void setNewOrder(final UserTrackOrder order, final Long newOrder) {
        order.updateOrder(newOrder);
        repository.save(order);
    }

    public void createNew(final Long userId) {
        final var newOrder = new UserTrackOrder(userId, 1L);
        newOrder.firstOrder();

        repository.save(newOrder);
    }


}
