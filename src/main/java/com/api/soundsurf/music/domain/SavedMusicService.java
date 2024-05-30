package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import com.api.soundsurf.list.entity.SavedMusic;
import com.api.soundsurf.music.exception.MusicNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SavedMusicService {
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final SavedMusicRepository savedMusicRepository;

    @Transactional
    public void saveMusic(final Long userId, final Long musicId) {
        final var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var music = musicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));

        savedMusicRepository.save(SavedMusic.newInstance(user, music));
    }

    @Transactional
    public void unsaveMusic(final Long userId, final Long musicId) {
        final var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var music = musicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));

        savedMusicRepository.deleteByUserAndMusic(user, music);
    }

    @Transactional
    public Boolean isSavedMusic(final Long userId, final Long musicId) {
        SavedMusic savedMusic = savedMusicRepository.findByUserIdAndMusicId(userId, musicId);

        Boolean isSaved = !Objects.isNull(savedMusic);
        return isSaved;
    }

    public List<SavedMusic> getSavedMusics(final Long userId) {
        return savedMusicRepository.findAllByUserId(userId);
    }
}
