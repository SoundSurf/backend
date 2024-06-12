package com.api.soundsurf.list.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "memo", nullable = true)
    private String memo;

    @Column(name= "is_complete", nullable = false)
    private boolean complete;

    @Column(name = "genre_id", nullable = true)
    private Long genreId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name="deleted", nullable = false)
    private boolean deleted;

    public Project(final  Long userId, final String name, final Long genreId) {
        this.userId = userId;
        this.name = name;
        this.genreId = genreId;
        this.complete = false;
        this.deleted = false;
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public void updateMemo(final String memo) {
        this.memo = memo;
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
