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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Autowired
    private final SessionTokenRepository sessionTokenRepository;

    private final String[] apiIgnoreUrl = {"/api-docs", "/swagger-ui/**", "/v3/**"};
    private final String[] userIgnoreUrl = {"/user/create", "/user/login"};
    private final String[] profileIgnoreUrl = {"/profile/cars", "/profile/genres"};
    private final ArrayList<String> tokenIgnoreUrl = new ArrayList<>(Stream.of(apiIgnoreUrl, userIgnoreUrl).flatMap(Arrays::stream).toList());

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter(sessionTokenRepository, tokenIgnoreUrl);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .cors().disable()
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