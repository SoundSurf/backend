package com.api.soundsurf.api.quartz;

import com.api.soundsurf.iam.domain.spotify.SpotifyTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
public class GetSpotifyAccessTokenJob implements Job {
    private final SpotifyTokenProvider spotifyTokenProvider;

    @Autowired
    public GetSpotifyAccessTokenJob(SpotifyTokenProvider spotifyTokenProvider) {
        this.spotifyTokenProvider = spotifyTokenProvider;
    }

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        spotifyTokenProvider.getAccessToken();
        log.info("This is a quartz job example, please fix me!" + LocalDateTime.now());
    }
}