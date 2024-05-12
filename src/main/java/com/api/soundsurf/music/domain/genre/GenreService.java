package com.api.soundsurf.music.domain.genre;

import com.api.soundsurf.music.entity.Genre;
import com.api.soundsurf.music.exception.GenreNotFoundException;
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

    public Genre findById(final Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException(id));
    }
}
