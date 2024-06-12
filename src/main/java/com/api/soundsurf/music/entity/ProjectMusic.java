package com.api.soundsurf.music.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.api.soundsurf.list.entity.Project;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "project_musics")
public class ProjectMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String memo;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Music music;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectMusic(final String meno, final Music music, final Project project) {
        this.memo = meno;
        this.music = music;
        this.project = project;
    }

    public static ProjectMusic newInstance(final String memo, final Music music, final Project project) {
        return new ProjectMusic(memo, music, project);
    }

    public void updateMemo(final String memo) {
        this.memo = memo;
    }
}
