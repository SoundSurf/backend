package com.api.soundsurf.api.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class QuartzJobGetSpotifyAccessToken {
    public void conf(final Scheduler scheduler) throws SchedulerException {
        final var job = newJob(GetSpotifyAccessTokenJob.class)
                .withIdentity("spotifyToken", "group1")
                .build();

        // Trigger the job to run now, and then every 40 seconds
        final var trigger = newTrigger()
                .withIdentity("spotifyToken", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(55)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
