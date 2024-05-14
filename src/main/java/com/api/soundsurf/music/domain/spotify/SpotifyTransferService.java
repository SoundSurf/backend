package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.constant.GenreType;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.dto.MusicDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyTransferService {
    private final UserRecommendationMusicService userRecommendationMusicService;
    private final SpotifyBusinessService businessService;
    private final DriveService driveService;

    @Transactional
    public MusicDto.Common.Song recommend(final List<GenreType> genres, final SessionUser sessionUser) {
        final var userId = sessionUser.getUserId();

        final var prevRecommendedMusics = userRecommendationMusicService.get(userId);

        return businessService.find(prevRecommendedMusics, genres, sessionUser.getUserId());
    }

}
