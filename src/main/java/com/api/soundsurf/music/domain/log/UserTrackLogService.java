package com.api.soundsurf.music.domain.log;

import com.api.soundsurf.music.entity.UserTrackLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTrackLogService {
    private final UserTrackLogRepository repository;

    public List<UserTrackLog> findAllPrev(final Long userId, final LocalDateTime deadLineTime) {
        return repository.findAllByUserIdAndCreatedAtBeforeOrderByCreatedAtAsc(userId, deadLineTime);
    }

    public UserTrackLog findPrev(final Long userId, final LocalDateTime deadLineTime) {
        return repository.findByUserIdAndCreatedAtBeforeOrderByCreatedAtDesc(userId, deadLineTime);
    }

    public UserTrackLog findPrev(final Long id, final Long userId) {
        return repository.findTopByUserIdAndIdBeforeOrderByIdDesc(userId, id);
    }

    public void playAndUpdate(final UserTrackLog nowPlayLog) {
        nowPlayLog.nowPlay();

        repository.save(nowPlayLog);
    }
}
