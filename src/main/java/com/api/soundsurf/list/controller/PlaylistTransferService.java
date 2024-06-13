package com.api.soundsurf.list.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.list.domain.PlaylistBusinessService;
import com.api.soundsurf.list.dto.PlaylistDto;
import com.api.soundsurf.list.entity.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistTransferService {
    private final PlaylistBusinessService businessService;

    public PlaylistDto.List.Response getPlaylistList(final SessionUser sessionUser) {
        final var playlists = businessService.find(sessionUser.getUserId());

        final List<PlaylistDto.List.PlaylistSummary> completePlaylists = playlists.stream()
                .filter(Playlist::isComplete)
                .map(playlist -> new PlaylistDto.List.PlaylistSummary(
                        playlist.getName(),
                        playlist.getCreatedAt(),
                        playlist.getPlaylistMusics().size()))
                .collect(Collectors.toList());

        final List<PlaylistDto.List.PlaylistSummary> unCompletePlaylists = playlists.stream()
                .filter(playlist -> !playlist.isComplete())
                .map(playlist -> new PlaylistDto.List.PlaylistSummary(
                        playlist.getName(),
                        playlist.getCreatedAt(),
                        playlist.getPlaylistMusics().size()))
                .collect(Collectors.toList());

        return new PlaylistDto.List.Response(completePlaylists, unCompletePlaylists);
    }

    public PlaylistDto.Get.Response getPlaylist(final SessionUser sessionUser, final Long playlistId) {
        final var playlist = businessService.getPlaylist(sessionUser.getUserId(), playlistId);
        final var musicCount = businessService.getMusicCount(playlist);
        final var genreIds = businessService.getGenreIds(playlist);
        final var playlistMusics = businessService.getPlaylistMusics(playlist);

        List<PlaylistDto.Get.MusicWithMemo> playlistMusicsWithMemo = playlistMusics.stream()
                .map(music -> {
                    final var memo = businessService.getPlaylistMusicMemo(playlist, music);
                    return new PlaylistDto.Get.MusicWithMemo(music, memo);
                })
                .collect(Collectors.toList());

        return new PlaylistDto.Get.Response(
                playlist.getId(),
                playlist.getName(),
                playlist.isComplete(),
                playlist.isDeleted(),
                playlist.getCreatedAt(),
                musicCount,
                genreIds,
                playlistMusicsWithMemo
        );
    }

    public PlaylistDto.Create.Response create(final SessionUser sessionUser, final PlaylistDto.Create.Request req) {
        final var playlistId = businessService.create(sessionUser.getUserId(), req.getName(), req.getGenreIds());

        return new PlaylistDto.Create.Response(playlistId);
    }

    public void addMusic(final SessionUser sessionUser, final Long playlistId, final PlaylistDto.Music.Request req) {
        businessService.addMusic(sessionUser.getUserId(), playlistId, req.getTrackId(), req.getTitle(), req.getArtists(), req.getImageUrl());
    }

    public void deleteMusic(final SessionUser sessionUser, final Long playlistId, final Long musicId) {
        businessService.deleteMusic(sessionUser.getUserId(), playlistId, musicId);
    }

    public void addMemo(final SessionUser sessionUser, final Long playlistId, final PlaylistDto.Memo.Request req) {
        businessService.addMemo(sessionUser.getUserId(), playlistId, req.getMusicId(), req.getMemo());
    }

    public void deleteMemo(final SessionUser sessionUser, final Long playlistId, final Long musicId) {
        businessService.deleteMemo(sessionUser.getUserId(), playlistId, musicId);
    }

    public void complete(final SessionUser sessionUser, final Long id) {
        businessService.complete(sessionUser.getUserId(), id);
    }

    public void unComplete(final SessionUser sessionUser, final Long id) {
        businessService.unComplete(sessionUser.getUserId(), id);
    }

    public void delete(final SessionUser sessionUser, final Long id) {
        businessService.delete(sessionUser.getUserId(), id);
    }
}
