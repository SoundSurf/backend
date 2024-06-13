package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.SavedMusic;
import com.api.soundsurf.music.domain.spotify.DriveService;
import com.api.soundsurf.music.dto.SavedMusicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedMusicBusinessService {
    private final SavedMusicService service;
    private final DriveService driveService;

    public void saveMusic(final User user, final List<String> musicIds) {
        final var savedSpotifyTrackId = service.getSavedMusics(user.getId()).stream().map(SavedMusic::getTrackId).toList();

        musicIds.stream()
                .filter(musicId -> !savedSpotifyTrackId.contains(musicId))
                .forEach(musicId -> {
                    final var track = driveService.getTrackInfo(musicId);
                    final var genre = driveService.getGenre(track.album().artists().get(0).id());

                    service.saveMusic(user, track, genre);
                });
    }

    public void delete(final Long userId, final String musicId) {
        service.delete(userId, musicId);
    }

    public List<SavedMusic> getSavedMusics(final Long userId) {
        return service.getSavedMusics(userId);
    }

    public SavedMusicDto.GetCount.Response getSavedMusicCount(final Long userId, final String musicId) {
        final var isSaved = service.isSavedMusic(userId, musicId);
        long count = service.getSavedMusicCount(userId);

        return new SavedMusicDto.GetCount.Response(count, isSaved);
    }
}
