package com.api.soundsurf.music.entity;

import com.api.soundsurf.list.entity.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "project_genres")
public class ProjectGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "genre_id")
    private Long genreId;

    public ProjectGenre(Project project, Long genreId) {
        this.project = project;
        this.genreId = genreId;
    }

    public static ProjectGenre newInstance(final Project project, final Long genreId) {
        return new ProjectGenre(project, genreId);
    }
}
