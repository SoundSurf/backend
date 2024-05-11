package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.spotify.DriveService;
import com.api.soundsurf.iam.domain.spotify.SearchService;
import com.api.soundsurf.iam.dto.MusicDto;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.domain.CrawlerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spotify")
public class SpotifyController {
    private final DriveService driveService;
    private final SearchService service;
    private final CrawlerService crawlerService;


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
            final @RequestBody @Valid MusicDto.Recommendation.Request request) {
        return driveService.recommendation(request);
    }

    @GetMapping("/")
    public String[] a() {
        return crawlerService.getMusicGenresRating("butter", "bts");
    }

    @GetMapping("/search/artist")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Search.Response.Artist searchArtist(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid MusicDto.Search.Request request) {
        return service.searchArtist(request);
    }

    @GetMapping("/search/album")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Search.Response.Album searchAlbum(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid MusicDto.Search.Request request) {
        return service.searchAlbum(request);
    }

    @GetMapping("/search/track")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Search.Response.Track searchTracks(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @Valid MusicDto.Search.Request request) {
        return service.searchTracks(request);
    }


    @GetMapping("/now-playing")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.NowPlaying.Response getNowPlaying(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @ModelAttribute @Valid String albumId) {
        return driveService.getNowPlayingAlbum(albumId);
    }


}
