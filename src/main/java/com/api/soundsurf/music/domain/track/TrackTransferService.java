package com.api.soundsurf.music.domain.track;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.spotify.SpotifyBusinessService;
import com.api.soundsurf.music.dto.MusicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrackTransferService {
    private final TrackBusinessService businessService;
    private final UserTrackLogService userTrackLogService;
    private final SpotifyBusinessService spotifyBusinessService;

    public MusicDto.Track previous(final SessionUser sessionUser, final Long logId) {
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var i = spotifyBusinessService.findPrevLogIndexInAllLogs(allLogs, logId);
        final var nowIndex = i - 1;
        final var track = spotifyBusinessService.findTrack(allLogs.get(nowIndex).getTrackId());

        final var song = new MusicDto.Common.Song(track);
        return new MusicDto.Track(song, (long) nowIndex, allLogs.size());
    }

    public MusicDto.Track following(final SessionUser sessionUser, final Long logId) {
        final var allLogs = userTrackLogService.findAllPrev(sessionUser.getUserId(), LocalDateTime.now().minusHours(24L));
        final var i = spotifyBusinessService.findPrevLogIndexInAllLogs(allLogs, logId);
        final var nowIndex = i + 1;
        final var track = spotifyBusinessService.findTrack(allLogs.get(nowIndex).getTrackId());

        final var song = new MusicDto.Common.Song(track);
        return new MusicDto.Track(song, (long) nowIndex, allLogs.size());
    }

}
