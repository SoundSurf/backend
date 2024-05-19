package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.music.entity.UserTrackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserTrackLogRepository extends JpaRepository<UserTrackLog, Long> {
    List<UserTrackLog> findAllByUserIdAndCreatedAtBeforeOrderByCreatedAtDesc(Long userId, LocalDateTime deadlineTime);
    UserTrackLog findByUserIdAndCreatedAtBeforeOrderByCreatedAtDesc(Long userId, LocalDateTime deadlineTime);
    UserTrackLog findTopByUserIdAndIdBeforeOrderByIdDesc(Long userId, Long id);
}
