package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.domain.user.UserRepository;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.PlaylistNotFoundException;
import com.api.soundsurf.iam.exception.UserNotFoundException;
import com.api.soundsurf.list.entity.Playlist;
import com.api.soundsurf.music.domain.MusicRepository;
import com.api.soundsurf.music.domain.genre.PlaylistGenreRepository;
import com.api.soundsurf.music.entity.Music;
import com.api.soundsurf.music.entity.PlaylistGenre;
import com.api.soundsurf.music.entity.PlaylistMusic;
import com.api.soundsurf.music.exception.MusicNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final PlaylistGenreRepository playlistGenreRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;
    private final SpotifyApi api;

    public void save(final Playlist playlist) {
        playlistRepository.save(playlist);
    }

    public boolean isExist(final Long userId, final String name) {
        return playlistRepository.existsByUserIdAndName(userId, name);
    }

    public Long create(final Long userId, final String name, final List<Long> genreIds) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var newPlaylist = playlistRepository.save(new Playlist(user, name));
        genreIds.forEach(genreId -> playlistGenreRepository.save(new PlaylistGenre(newPlaylist, genreId)));
        return newPlaylist.getId();
    }

    public void addMusic(final Long userId, final Long playlistId, final String trackId, final String imageUrl) {
        try {
            final var track = api.getTrack(trackId).build().execute();
            final var music = musicRepository.save(new Music(trackId, track.getName(), track.getArtists()[0].getName(), imageUrl));
            final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            final var playlist = playlistRepository.findByIdAndUser(playlistId, user).orElseThrow(() -> new PlaylistNotFoundException(playlistId));
            playlistMusicRepository.save(new PlaylistMusic(null, music, playlist));
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new MusicNotFoundException(trackId);
        }
    }

    @Transactional
    public void deleteMusic(final Long userId, final Long playlistId, final Long musicId) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var playlist = playlistRepository.findByIdAndUser(playlistId, user).orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        playlistMusicRepository.deleteByPlaylistIdAndMusicId(playlist.getId(), musicId);
    }

    public void addMemo(final Long userId, final Long playlistId, final Long musicId, final String memo) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var playlist = playlistRepository.findByIdAndUser(playlistId, user).orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        final var music = musicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));
        final var playlistMusic = playlistMusicRepository.findByPlaylistAndMusic(playlist, music);
        playlistMusic.updateMemo(memo);
        playlistMusicRepository.save(playlistMusic);
    }

    public void deleteMemo(final Long userId, final Long playlistId, final Long musicId) {
        final var user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final var playlist = playlistRepository.findByIdAndUser(playlistId, user).orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        final var music = musicRepository.findById(musicId).orElseThrow(() -> new MusicNotFoundException(musicId));
        final var playlistMusic = playlistMusicRepository.findByPlaylistAndMusic(playlist, music);
        playlistMusic.updateMemo(null);
        playlistMusicRepository.save(playlistMusic);
    }

    public Playlist findNotNullable(final Long userId, final Long id) {
        final var playlist = playlistRepository.findByUserIdAndId(userId, id);

        if (playlist == null) {
            throw new PlaylistNotFoundException(id);
        }

        return playlist;
    }

    public List<Playlist> find(final Long userId) {
        return playlistRepository.findAllByUserIdAndDeletedIsFalse(userId);
    }

    public Playlist findByIdAndUser(Long playlistId, User user) {
        return playlistRepository.findByIdAndUser(playlistId, user)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
    }

    public int countMusicByPlaylist(Playlist playlist) {
        return playlistMusicRepository.countByPlaylist(playlist);
    }

    public List<Long> findGenreIdByPlaylist(Playlist playlist) {
        return playlistGenreRepository.findGenreIdByPlaylist(playlist);
    }

    public List<Music> findMusicByPlaylist(Playlist playlist) {
        return playlistMusicRepository.findMusicByPlaylist(playlist);
    }

    public PlaylistMusic findByPlaylistAndMusic(Playlist playlist, Music music) {
        return playlistMusicRepository.findByPlaylistAndMusic(playlist, music);
    }
}
