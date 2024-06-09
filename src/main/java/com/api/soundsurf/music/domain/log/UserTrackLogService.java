package com.api.soundsurf.music.domain.log;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.domain.spotify.Utils;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import com.api.soundsurf.music.entity.UserTrackLog;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTrackLogService {
    private final UserTrackLogRepository repository;
    private final CrawlerService crawlerService;

    public void createPrevSongLog(final Long lastOrder, final MusicDto.Common.Song song, final User user) {
        createLog(lastOrder - 1L, song, user);
    }

    public void createNextSongLog(final Long lastOrder, final MusicDto.Common.Song song, final User user) {
        createLog(lastOrder + 1L, song, user);
    }


    public void createNextSongLog(List<UserRecommendationMusic> prevRecommendedMusics, final MusicDto.Common.Song song, final User user) {
        final var lastOrder = prevRecommendedMusics.get(prevRecommendedMusics.size() - 1).getOrder();
        createNextSongLog(lastOrder, song, user);
    }

    public List<UserTrackLog> findAllPrev(final Long userId, final LocalDateTime deadLineTime) {
        return repository.findAllByUserIdAndCreatedAtBeforeOrderByCreatedAtAsc(userId, deadLineTime);
    }

    public UserTrackLog findPrev(final Long userId, final LocalDateTime deadLineTime) {
        return repository.findByUserIdAndCreatedAtBeforeOrderByCreatedAtDesc(userId, deadLineTime);
    }

    private void createLog(final Long order, final MusicDto.Common.Song song, final User user) {
        final var crawlResult = crawlerService.getAlbumGenresRating(song.album().albumName(), song.album().artists().stream().map(MusicDto.ArtistSimpleInfo.Musician::artistName).toArray(String[]::new));
        final var albumGenre = crawlResult[0];
        final var albumRating = crawlResult[1];
        final var albumMetadata = Utils.albumDtoToString(song.album(), albumGenre, albumRating);

        final var jsonMusicians = new JSONArray();
        song.artists().forEach(e -> Utils.hydrateJsonMusicians(e, jsonMusicians));

        final var artistsMetadata = Utils.musicianJsonToString(jsonMusicians);

        final var log = new UserTrackLog(user.getId(), song.id(), order, song.name(), song.album().images(), song.previewUrl(), song.spotifyUrl(), song.durationMs(), artistsMetadata, albumMetadata, song.album().releaseDate());

        repository.save(log);
    }
}
