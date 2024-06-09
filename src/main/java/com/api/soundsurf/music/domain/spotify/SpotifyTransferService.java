package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.exception.UserGenreCountException;
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
    private final UserBusinessService userBusinessService;

    @Transactional
    public MusicDto.Track recommend(final List<Integer> genres, final SessionUser sessionUser) {
        if (genres != null && genres.size() > 3)
            throw new UserGenreCountException();

        final var response = new MusicDto.Track();
        final var user = userBusinessService.getUser(sessionUser.getUserId());

        final var prevRecommendedMusics = userRecommendationMusicService.get(sessionUser.getUserId());

        return businessService.findAndMakeLog(prevRecommendedMusics, genres, user, response);
    }

}
