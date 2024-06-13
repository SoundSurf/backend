package com.api.soundsurf.music.domain;

import com.api.soundsurf.list.entity.SavedMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SavedMusicRepository extends JpaRepository<SavedMusic, Long> {
    void deleteByUserIdAndTrackId(Long user, String trackId);

    SavedMusic findByUserIdAndTrackId(Long userId, String trackId);

    List<SavedMusic> findAllByUserId(Long userId);

    @Query("SELECT COUNT(sm) FROM SavedMusic sm WHERE sm.user.id = :userId AND sm.savedAt BETWEEN :startOfDay AND :endOfDay")
    long countSavedMusicsByUserAndDay(@Param("userId") Long userId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

}