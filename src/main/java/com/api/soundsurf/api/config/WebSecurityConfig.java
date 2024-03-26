package com.api.soundsurf.api.config;

import com.api.soundsurf.iam.domain.SessionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Autowired
    private final SessionTokenRepository sessionTokenRepository;

    private final  ArrayList<String> tokenIgnoreUrl = new ArrayList<>(List.of("/user/**"));

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter(sessionTokenRepository, tokenIgnoreUrl);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .addFilterAfter(tokenFilter(), BasicAuthenticationFilter.class);
//                .authorizeHttpRequests((requests) -> requests
//                                .requestMatchers("/user/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//                .httpBasic().disable()
//                .logout().disable();
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());

        return http.build();
    }

}