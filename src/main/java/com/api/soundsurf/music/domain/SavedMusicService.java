package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import com.api.soundsurf.list.entity.SavedMusic;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicRepository;
import com.api.soundsurf.music.exception.MusicNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SavedMusicService {
    private final UserRepository userRepository;
    private final UserRecommendationMusicRepository userRecommendationMusicRepository;
    private final SavedMusicRepository savedMusicRepository;

    @Transactional
    public void saveMusic(final Long userId, final Long musicId) {
        final var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var music = userRecommendationMusicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));

        savedMusicRepository.save(SavedMusic.newInstance(user, music));
    }

    @Transactional
    public void unsaveMusic(final Long userId, final Long musicId) {
        final var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var music = userRecommendationMusicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));

        savedMusicRepository.deleteByUserAndUserRecommendationMusic(user, music);
    }

    @Transactional
    public Boolean isSavedMusic(final Long userId, final Long musicId) {
        SavedMusic savedMusic = savedMusicRepository.findByUserIdAndUserRecommendationMusicId(userId, musicId);

        Boolean isSaved = !Objects.isNull(savedMusic);
        return isSaved;
    }

    @Transactional
    public long getSavedMusicCount(final Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return savedMusicRepository.countSavedMusicsByUserAndDay(userId, startOfDay, endOfDay);
    }

    public List<SavedMusic> getSavedMusics(final Long userId) {
        return savedMusicRepository.findAllByUserId(userId);
    }
}
