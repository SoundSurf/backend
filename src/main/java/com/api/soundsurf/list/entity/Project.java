package com.api.soundsurf.list.entity;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.music.entity.ProjectGenre;
import com.api.soundsurf.music.entity.ProjectMusic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "is_complete", nullable = false)
    private boolean isComplete;

    @Column(name="isDeleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectGenre> projectGenres = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMusic> projectMusics = new ArrayList<>();

    public Project(final User user, final String name) {
        this.user = user;
        this.name = name;
        this.isComplete = false;
        this.isDeleted = false;
    }

//    public void updateName(final String name) {
//        this.name = name;
//    }
//
//    public void updateMemo(final String memo) {
//        this.memo = memo;
//    }
//
//    public void complete() {
//        this.complete = true;
//    }
//
//    public void unComplete() {
//        this.complete = false;
//    }
//
//    public void delete() {
//        this.deleted = true;
//    }
}
