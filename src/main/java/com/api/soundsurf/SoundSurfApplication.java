package com.api.soundsurf;

import com.api.soundsurf.api.config.DataSourcePropertyLoader;
import com.api.soundsurf.api.quartz.QuartzJobGetSpotifyAccessToken;
import com.api.soundsurf.music.HttpClientSingleton;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.web.WebApplicationInitializer;

@Slf4j
@SpringBootApplication
public class SoundSurfApplication extends SpringBootServletInitializer implements WebApplicationInitializer, ApplicationListener<ApplicationReadyEvent> {
    private final QuartzJobGetSpotifyAccessToken quartzJobGetSpotifyAccessToken;

    private static final Class<SoundSurfApplication> APPLICATION_SOURCE = SoundSurfApplication.class;

    public SoundSurfApplication(QuartzJobGetSpotifyAccessToken quartzJobConfiguration) {
        this.quartzJobGetSpotifyAccessToken = quartzJobConfiguration;
    }

    public static void main(String[] args) {
        try {
            HttpClientSingleton.createHttpClient();
            new SpringApplicationBuilder(APPLICATION_SOURCE).listeners(new DataSourcePropertyLoader()).run(args);
        } finally {
            HttpClientSingleton.closeHttpClient();
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        startQuartz();
        log.info("SoundSurf application successfully started.");
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    private void startQuartz() {
        final var schedulerFactory = new StdSchedulerFactory();
        try {
            final var scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            quartzJobGetSpotifyAccessToken.conf(scheduler);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        this.setRegisterErrorPageFilter(false);
        return application.sources(APPLICATION_SOURCE).listeners(new DataSourcePropertyLoader());
    }

}
