package com.api.soundsurf.list.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.entity.ProjectGenre;
import com.api.soundsurf.music.entity.ProjectMusic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "playlists")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "complete", nullable = false)
    private boolean complete;

    @Column(name="deleted", nullable = false)
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectGenre> projectGenres = new ArrayList<>();

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMusic> projectMusics = new ArrayList<>();

    public Project(final User user, final String name) {
        this.user = user;
        this.name = name;
        this.complete = false;
        this.deleted = false;
    }

    @PrePersist
    private void onPersist() {
        if (this.getCreatedAt() == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public void complete() {
        this.complete = true;
    }

    public void unComplete() {
        this.complete = false;
    }

    public void delete() {
        this.deleted = true;
    }
}
