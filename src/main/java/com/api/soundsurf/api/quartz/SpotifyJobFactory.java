package com.api.soundsurf.api.quartz;

import com.api.soundsurf.music.domain.spotify.SpotifyTokenProvider;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpotifyJobFactory implements JobFactory {

    private final SpotifyTokenProvider spotifyTokenProvider;

    @Autowired
    public SpotifyJobFactory(SpotifyTokenProvider spotifyTokenProvider) {
        this.spotifyTokenProvider = spotifyTokenProvider;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        if (bundle.getJobDetail().getJobClass().equals(GetSpotifyAccessTokenJob.class)) {
            return new GetSpotifyAccessTokenJob(spotifyTokenProvider);
        }
        throw new SchedulerException("Invalid job class");
    }
}