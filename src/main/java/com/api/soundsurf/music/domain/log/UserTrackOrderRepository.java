package com.api.soundsurf.music.domain.log;

import com.api.soundsurf.music.entity.UserTrackOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackOrderRepository extends JpaRepository<UserTrackOrder, Long> {
    UserTrackOrder findByUserId(final Long userId);

}
