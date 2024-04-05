package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAll();
    Genre findByName(final String name);
}
