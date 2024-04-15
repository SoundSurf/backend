package com.api.soundsurf.api.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

import static com.api.soundsurf.api.Const.SPOTIFY_ACCESS_TOKEN;

public class GetSpotifyAccessTokenJob implements Job {
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.setProperty(SPOTIFY_ACCESS_TOKEN, "abc");
        System.out.println("This is a quartz job example, please fix me!" + LocalDateTime.now());
//        System.out.println(System.getProperty(SPOTIFY_ACCESS_TOKEN));
    }
}