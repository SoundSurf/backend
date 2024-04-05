package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findByName(final String name) {
        return genreRepository.findByName(name);
    }
}
