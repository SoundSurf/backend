package com.api.soundsurf.music.controller;

import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.music.constant.SearchType;
import com.api.soundsurf.music.domain.spotify.DriveService;
import com.api.soundsurf.music.domain.spotify.SearchService;
import com.api.soundsurf.music.domain.spotify.SpotifyTransferService;
import com.api.soundsurf.music.dto.MusicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spotify")
@CrossOrigin("*")
public class SpotifyController {
    private final DriveService driveService;
    private final SearchService searchService;
    private final SpotifyTransferService transferService;


    @GetMapping("/recommendation")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.Track recommendation(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @RequestParam(required = false) List<Integer> genres) {

        return transferService.recommend(genres, sessionUser);
    }

    @GetMapping("/search")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.SearchResult search(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @RequestParam() String title,
            final @RequestParam int limit,
            final @RequestParam int offset,
            final @RequestParam SearchType type) {
        return searchService.search(title, limit, offset, type);
    }


    @GetMapping("/album-info")
    @Operation(
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER,
                            required = true, content = @Content(mediaType = "application/json"))
            })
    public MusicDto.NowPlaying.Response getAlbumInfo(
            final @AuthenticationPrincipal SessionUser sessionUser,
            final @RequestParam String albumId) {
        return driveService.getAlbumInfo(albumId);
    }


}
