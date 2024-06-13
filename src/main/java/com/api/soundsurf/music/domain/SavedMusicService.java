package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.SavedMusic;
import com.api.soundsurf.music.dto.MusicDto;
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
    private final SavedMusicRepository repository;

    public void saveMusic(final User user, final MusicDto.Common.Song trackDto, String[] genre) {
        final var newSavedMusic = new SavedMusic(user, trackDto, genre);

        repository.save(newSavedMusic);
    }

    @Transactional
    public void delete(final Long userId, final String musicId) {
        repository.deleteByUserIdAndTrackId(userId, musicId);
    }

    @Transactional
    public boolean isSavedMusic(final Long userId, final String trackId) {
        SavedMusic savedMusic = repository.findByUserIdAndTrackId(userId, trackId);

        return !Objects.isNull(savedMusic);
    }

    @Transactional
    public long getSavedMusicCount(final Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return repository.countSavedMusicsByUserAndDay(userId, startOfDay, endOfDay);
    }

    public List<SavedMusic> getSavedMusics(final Long userId) {
        return repository.findAllByUserId(userId);
    }
}
