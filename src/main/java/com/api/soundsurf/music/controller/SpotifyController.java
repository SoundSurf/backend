package com.api.soundsurf.music.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.domain.spotify.DriveService;
import com.api.soundsurf.music.domain.spotify.SearchService;
import com.api.soundsurf.music.domain.spotify.SpotifyTransferService;
import com.api.soundsurf.music.dto.MusicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spotify")
public class SpotifyController {
    private final DriveService driveService;
    private final SearchService searchService;
    private final CrawlerService crawlerService;
    private final SpotifyTransferService transferService;

    @GetMapping("/recommendation")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Common.Song recommendation(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid MusicDto.Recommendation.Request request) {

        return transferService.recommend(request, sessionUser);
    }

    @GetMapping("/search")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.SearchResult search(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid MusicDto.Search.Request request) {
        return searchService.search(request);
    }


    @GetMapping("/now-playing")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.NowPlaying.Response getNowPlaying(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid String albumId) {
        return driveService.getNowPlayingAlbum(albumId);
    }


}
