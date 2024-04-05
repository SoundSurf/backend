package com.api.soundsurf.list.entity;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.entity.Music;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "saved_musics")
public class SavedMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    public SavedMusic(User user, Music music) {
        this.user = user;
        this.music = music;
    }

    public static final SavedMusic newInstance(User user, Music music) {
        return new SavedMusic(user, music);
    }
}
