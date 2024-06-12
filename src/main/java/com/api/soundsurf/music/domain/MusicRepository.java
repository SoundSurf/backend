package com.api.soundsurf.music.domain;

import com.api.soundsurf.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByTrackId(String trackId);
}
