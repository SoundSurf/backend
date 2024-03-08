package com.api.soundsurf;

import com.api.soundsurf.api.config.DataSourcePropertyLoader;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.web.WebApplicationInitializer;

@Slf4j
@SpringBootApplication
public class SoundSurfApplication extends SpringBootServletInitializer implements WebApplicationInitializer, ApplicationListener<ApplicationReadyEvent> {

    private static final Class<SoundSurfApplication> APPLICATION_SOURCE = SoundSurfApplication.class;

    public static void main(String[] args) {
        new SpringApplicationBuilder(APPLICATION_SOURCE).listeners(new DataSourcePropertyLoader()).run(args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("SoundSurf application successfully started.");
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        this.setRegisterErrorPageFilter(false);
        return application.sources(APPLICATION_SOURCE).listeners(new DataSourcePropertyLoader());
    }

}
