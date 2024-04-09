package com.api.soundsurf.music.domain;

import com.api.soundsurf.music.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAll();
    Genre findByName(final String name);
}
