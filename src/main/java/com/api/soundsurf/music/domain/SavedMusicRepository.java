package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.entity.SavedMusic;
import com.api.soundsurf.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedMusicRepository extends JpaRepository<SavedMusic, Long> {
    void deleteByUserAndMusic(User user, Music music);

    SavedMusic findByUserIdAndMusicId(Long userId, Long musicId);

}
