package com.api.soundsurf.iam.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.api.soundsurf.list.entity.SavedMusic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Persistable<Long> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = true)
    private String nickname;

    @Column(name = "new_user", nullable = false)
    private Boolean newUser = true;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile userProfile;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_id")
    private Qr qr;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenre> userGenres = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedMusic> savedMusics = new ArrayList<>();

    @PrePersist
    private void onPersist() {
        if (this.getCreatedAt() == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    public User(final String email, final String password, final Qr qr, final UserProfile userProfile, final Car car) {
        this.email = email;
        this.password = password;
        this.qr = qr;
        this.userProfile = userProfile;
        this.car = car;
    }
}
