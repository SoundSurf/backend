package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.Playlist;
import com.api.soundsurf.list.exception.DuplicatePlaylistNameException;
import com.api.soundsurf.music.entity.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistBusinessService {
    private final PlaylistService playlistService;
    private final UserService userService;

    public List<Playlist> find(final Long userId) {
        return playlistService.find(userId);
    }

    public Playlist getPlaylist(Long userId, Long playlistId) {
        User user = userService.findById(userId);
        return playlistService.findByIdAndUser(playlistId, user);
    }

    public int getMusicCount(Playlist playlist) {
        return playlistService.countMusicByPlaylist(playlist);
    }

    public List<Long> getGenreIds(Playlist playlist) {
        return playlistService.findGenreIdByPlaylist(playlist);
    }

    public List<Music> getPlaylistMusics(Playlist playlist) {
        return playlistService.findMusicByPlaylist(playlist);
    }

    public String getPlaylistMusicMemo(Playlist playlist, Music music) {
        final var playlistMusic = playlistService.findByPlaylistAndMusic(playlist, music);
        return playlistMusic != null ? playlistMusic.getMemo() : null;
    }

    public Long create(final Long userId, final String name, final List<Long> genreIds) {
        validateName(userId, name);

        return playlistService.create(userId, name, genreIds);
    }

    public void addMusic(final Long userId, final Long playlistId, final String trackId, final String imageUrl) {
        playlistService.addMusic(userId, playlistId, trackId, imageUrl);
    }

    public void deleteMusic(final Long userId, final Long playlistId, final Long musicId) {
        playlistService.deleteMusic(userId, playlistId, musicId);
    }

    public void addMemo(final Long userId, final Long playlistId, final Long musicId, final String memo) {
        playlistService.addMemo(userId, playlistId, musicId, memo);
    }

    public void deleteMemo(final Long userId, final Long playlistId, final Long musicId) {
        playlistService.deleteMemo(userId, playlistId, musicId);
    }

    public void complete(final Long userId, final Long id) {
        final var playlist = playlistService.findNotNullable(userId, id);

        patchPlaylist(playlist, true);
    }

    public void unComplete(final Long userId, final Long id) {
        final var playlist = playlistService.findNotNullable(userId, id);

        patchPlaylist(playlist, false);
    }

    public void delete(final Long userId, final Long id) {
        final var playlist = playlistService.findNotNullable(userId, id);

        playlist.delete();
        playlistService.save(playlist);
    }

    private void validateName(final Long userId, final String name) {
        if (playlistService.isExist(userId, name)) {
            throw new DuplicatePlaylistNameException(name);
        }
    }

    private void patchPlaylist(final Playlist playlist, final boolean isComplete) {
        if (isComplete) {
            playlist.complete();
        } else {
            playlist.unComplete();
        }

        playlistService.save(playlist);
    }
}
