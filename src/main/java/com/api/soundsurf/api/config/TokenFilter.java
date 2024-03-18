package com.api.soundsurf.api.config;

import com.api.soundsurf.iam.domain.SessionTokenRepository;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.exception.UnauthorizedTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.api.soundsurf.api.Const.TOKEN_HEADER;

public class TokenFilter extends OncePerRequestFilter {
    private final SessionTokenRepository sessionTokenRepository;

    public TokenFilter(SessionTokenRepository sessionTokenRepository) {
        this.sessionTokenRepository = sessionTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract token from request header
        String token = request.getHeader("Authorization");

        if (token.isEmpty() || !token.startsWith(TOKEN_HEADER)) {

            throw new UnauthorizedTokenException();
        }

        token = token.substring(TOKEN_HEADER.length());
        //TODO : 헤더에 유저 uuid 보내기

        final var userUuid = validateTokenAndGetUserUuid(token);
        final var SessionUser = new SessionUser(userUuid);

        final var authentication = new UsernamePasswordAuthenticationToken(SessionUser, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String validateTokenAndGetUserUuid(String token) {
        final var sessionTokens = sessionTokenRepository.findAllByTokenAndCreatedAtBefore(token, LocalDateTime.now());
        if (sessionTokens.size() == 1) {
            return sessionTokens.get(0).getUserUuid();
        }
        ;

        throw new UnauthorizedTokenException();
    }

}
