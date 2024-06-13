package com.api.soundsurf.list.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    boolean existsByUserIdAndName(Long userId, String name);
    Optional<Playlist> findByIdAndUser(Long id, User user);
    Playlist findByUserIdAndId(Long userId, Long id);
    List<Playlist> findAllByUserIdAndDeletedIsFalse(Long userId);
}
