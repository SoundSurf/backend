package com.api.soundsurf.iam.controller;

import com.api.soundsurf.iam.domain.SpotifyService;
import com.api.soundsurf.iam.dto.MusicDto;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spotify")
public class SpotifyController {
    private final SpotifyService service;


    @GetMapping("/search")
    public List<MusicDto.Search.Response> search(@Valid @RequestBody MusicDto.Search.Request request) throws IOException, ParseException, SpotifyWebApiException {
        return service.search(request);
    }

    @GetMapping("/genres")
    public MusicDto.Genre.Response getGenres() throws IOException, ParseException, SpotifyWebApiException {
        return service.getGenres();
    }


}
