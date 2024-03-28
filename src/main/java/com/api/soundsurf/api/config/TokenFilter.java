package com.api.soundsurf.api.config;

import com.api.soundsurf.api.exception.ExceptionCode;
import com.api.soundsurf.iam.domain.SessionTokenRepository;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.exception.UnauthorizedTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.api.soundsurf.api.Const.TOKEN_HEADER;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {
    private final ArrayList<String> tokenIgnoreUrl;
    private final SessionTokenRepository sessionTokenRepository;

    public TokenFilter(SessionTokenRepository sessionTokenRepository, final ArrayList<String> tokenIgnoreUrl) {
        this.tokenIgnoreUrl = tokenIgnoreUrl;
        this.sessionTokenRepository = sessionTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI() + " comes");
        if (ignore(request.getRequestURI())) {
            log.info(request.getRequestURI() + " filter pass");
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token==null || !token.startsWith(TOKEN_HEADER)) {
            log.error(ExceptionCode.API.UNAUTHORIZED_TOKEN_EXCEPTION);
            throw new UnauthorizedTokenException();
        }

        token = token.substring(TOKEN_HEADER.length());

        //FIXME: after creating session token, push real user id
        final var userUuid = validateTokenAndGetUserId(token);
        final var SessionUser = new SessionUser(1L);

        final var authentication = new UsernamePasswordAuthenticationToken(SessionUser, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info(request.getRequestURI() + " filter pass");
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
