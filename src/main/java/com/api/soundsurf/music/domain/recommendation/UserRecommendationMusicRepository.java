package com.api.soundsurf.music.domain.recommendation;

import com.api.soundsurf.music.entity.UserRecommendationMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRecommendationMusicRepository extends JpaRepository<UserRecommendationMusic, Long> {
    UserRecommendationMusic findByUserIdAndOrder(Long userId, Long order);
    List<UserRecommendationMusic> findAllByUserIdOrderByOrder(Long userId);
    UserRecommendationMusic findByUserIdAndId(Long userId, Long id);
    Optional<UserRecommendationMusic> findByTrackId(String trackId);
}
