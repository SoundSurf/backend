package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.entity.SavedMusic;
import com.api.soundsurf.music.dto.SavedMusicDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedMusicTransferService {
    private final SavedMusicBusinessService businessService;

    @Transactional
    public SavedMusicDto.GetAll.Response getSavedMusics(final SessionUser sessionUser) {
        List<SavedMusic> savedMusics = businessService.getSavedMusics(sessionUser.getUserId());

        return new SavedMusicDto.GetAll.Response(
                savedMusics.stream().map(SavedMusicDto.SavedMusic::new).toList());
    }
}
