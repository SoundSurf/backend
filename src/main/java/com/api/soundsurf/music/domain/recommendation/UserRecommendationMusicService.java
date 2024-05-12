package com.api.soundsurf.music.domain.recommendation;

import com.api.soundsurf.music.entity.UserRecommendationMusic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecommendationMusicService {
    private UserRecommendationMusicRepository repository;

    public List<UserRecommendationMusic> get(final Long userId) {
        return repository.findAllByUserIdAndDeletedIsFalseOrderByOrder(userId);
    }

    public UserRecommendationMusic get(final Long userId, final Long id) {
        return repository.findByUserIdAndIdAndDeletedIsFalse(userId, id);
    }

    public void save(final UserRecommendationMusic music) {
        repository.save(music);
    }



}
