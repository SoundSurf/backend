package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreBusinessService {
    private final GenreService genreService;
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}
