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
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.api.soundsurf.api.Const.TOKEN_HEADER;

public class TokenFilter extends OncePerRequestFilter {
    private final ArrayList<String> tokenIgnoreUrl;
    private final SessionTokenRepository sessionTokenRepository;

    public TokenFilter(SessionTokenRepository sessionTokenRepository, final ArrayList<String> tokenIgnoreUrl) {
        this.tokenIgnoreUrl = tokenIgnoreUrl;
        this.sessionTokenRepository = sessionTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (ignore(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (!token.startsWith(TOKEN_HEADER)) {
            throw new UnauthorizedTokenException();
        }

        token = token.substring(TOKEN_HEADER.length());

        final var userUuid = validateTokenAndGetUserId(token);
        final var SessionUser = new SessionUser(userUuid);

        final var authentication = new UsernamePasswordAuthenticationToken(SessionUser, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean ignore(final String requestUrl) {
        return tokenIgnoreUrl.stream().anyMatch(ignoreUrl -> {
            final var matcher = FileSystems.getDefault().getPathMatcher("glob:"+ignoreUrl);
            return matcher.matches(Paths.get(requestUrl));
        });
    }

    private Long validateTokenAndGetUserId(String token) {
        final var sessionTokens = sessionTokenRepository.findAllByTokenAndCreatedAtBefore(token, LocalDateTime.now());
        if (sessionTokens.size() == 1) {
            return sessionTokens.get(0).getUserId();
        }

        throw new UnauthorizedTokenException();
    }

}
