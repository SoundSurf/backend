package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.recommendation.UserRecommendationMusicService;
import com.api.soundsurf.music.dto.MusicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotifyTransferService {
    private final UserRecommendationMusicService userRecommendationMusicService;
    private final DriveService driveService;

    public MusicDto.Common.Response recommend(final MusicDto.Recommendation.Request request, final SessionUser sessionUser) {
        final var userId = sessionUser.getUserId();

        final var prevRecommendedMusics = userRecommendationMusicService.get(userId);

        if (prevRecommendedMusics == null | prevRecommendedMusics.size() == 0) {
            final var recommendations = driveService.recommendation(request);
            System.out.println("save event");
            return new MusicDto.Common.Response(recommendations[0]);

        } else if (prevRecommendedMusics.size() <= 3) {
            final var nowRecommendedMusic = prevRecommendedMusics.get(0);
            System.out.println("get recommend event");
            return new MusicDto.Common.Response(nowRecommendedMusic);
        }

        return new MusicDto.Common.Response(prevRecommendedMusics.get(0));
    }

}
