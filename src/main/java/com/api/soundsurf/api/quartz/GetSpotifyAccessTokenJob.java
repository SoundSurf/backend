package com.api.soundsurf.api.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

import static com.api.soundsurf.api.Const.SPOTIFY_ACCESS_TOKEN;

@Slf4j
public class GetSpotifyAccessTokenJob implements Job {
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.setProperty(SPOTIFY_ACCESS_TOKEN, "abc");
        log.info("This is a quartz job example, please fix me!" + LocalDateTime.now());
//        System.out.println(System.getProperty(SPOTIFY_ACCESS_TOKEN));
    }
}