package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.SpotifyService;
import com.api.soundsurf.iam.dto.MusicDto;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.CrawlerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spotify")
public class SpotifyController {
    private final SpotifyService service;
    private final CrawlerService crawlerService;

    @GetMapping("/search")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Common.Response search(
            final @AuthenticationPrincipal SessionUser sessionUser,
            @Valid @RequestBody MusicDto.Search.Request request) {
        return service.search(request);
    }

    @GetMapping("/genres")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Genre.Response getGenres(
            final @AuthenticationPrincipal SessionUser sessionUser
    ) {
        return service.getGenres();
    }

    @GetMapping("/recommendation")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Common.Response recommendation(
            final @AuthenticationPrincipal SessionUser sessionUser,
            @Valid @RequestBody MusicDto.Recommendation.Request request) {
        return service.recommendation(request);
    }

    @GetMapping("/")
    public String[] a () {
        return crawlerService.getMusicGenresRating("butter", "bts");
    }
    
}
