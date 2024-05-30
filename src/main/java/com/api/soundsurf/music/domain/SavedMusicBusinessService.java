package com.api.soundsurf.music.domain;

import com.api.soundsurf.list.entity.SavedMusic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedMusicBusinessService {
    private final SavedMusicService savedMusicService;

    public List<SavedMusic> getSavedMusics(final Long userId) {
        return savedMusicService.getSavedMusics(userId);
    }
}
