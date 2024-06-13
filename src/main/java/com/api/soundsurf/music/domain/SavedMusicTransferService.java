package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
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
    private final UserBusinessService userBusinessService;

    @Transactional
    public void saveMusic(final SessionUser sessionUser, final List<String> spotifyIds){
        final var user = userBusinessService.getUser(sessionUser.getUserId());

        businessService.saveMusic(user, spotifyIds);
    }

    public void delete(final SessionUser sessionUser, final String musicId) {
        businessService.delete(sessionUser.getUserId(), musicId);
    }

    @Transactional
    public SavedMusicDto.GetAll.Response getSavedMusics(final SessionUser sessionUser) {
        List<SavedMusic> savedMusics = businessService.getSavedMusics(sessionUser.getUserId());

        return new SavedMusicDto.GetAll.Response(
                savedMusics.stream().map(SavedMusicDto.SavedMusic::new).toList());
    }

    public SavedMusicDto.GetCount.Response getSavedMusicCount(final SessionUser sessionUser, final String musicId) {
        return businessService.getSavedMusicCount(sessionUser.getUserId(), musicId);
    }
}
