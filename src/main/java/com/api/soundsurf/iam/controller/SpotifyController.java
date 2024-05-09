package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.SpotifyService;
import com.api.soundsurf.iam.domain.spotify.DriveService;
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
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spotify")
public class SpotifyController {
    private final DriveService driveService;
    private final SpotifyService service;
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
            @Valid @RequestBody MusicDto.Recommendation.Request request) {
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
    public Artist[] searchArtist(
            final @AuthenticationPrincipal SessionUser sessionUser,
            @Valid @RequestBody MusicDto.Search.Request request) {
        return service.searchArtist(request.getTitle());
    }

    @GetMapping("/search/album")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public AlbumSimplified[] searchAlbum(
            final @AuthenticationPrincipal SessionUser sessionUser,
            @Valid @RequestBody MusicDto.Search.Request request) {
        return service.searchAlbum(request.getTitle());
    }

    @GetMapping("/search/track")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public Track[] searchTracks(
            final @AuthenticationPrincipal SessionUser sessionUser,
            @Valid @RequestBody MusicDto.Search.Request request) {
        return service.searchTracks2(request.getTitle());
    }

    @GetMapping("/now-playing")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.NowPlaying.Response getNowPlaying(
            final @AuthenticationPrincipal SessionUser sessionUser,
            @Valid String albumId) {
        return driveService.getNowPlaying(albumId);
    }


}
