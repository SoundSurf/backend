package com.api.soundsurf.iam.entity;

import com.api.soundsurf.api.utils.LocalDateTimeUtcSerializer;
import com.api.soundsurf.list.entity.SavedMusic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private boolean newUser = true;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeUtcSerializer.class)
    private LocalDateTime createdAt;

    @Column(name = "car_id", nullable = true)
    private Long carId;

    @Column(name = "image_S3_bucket_path", nullable = true)
    private String imageS3BucketPath;

    @Column(name = "qr_S3_bucket_path", nullable = true)
    private String qrS3BucketPath;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenre> userGenres = new ArrayList<>();

    @JsonManagedReference
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

    public User(final String email, final String password, final String qrS3BucketPath, final String imageS3BucketPath, final Long carId) {
        this.email = email;
        this.password = password;
        this.qrS3BucketPath = qrS3BucketPath;
        this.imageS3BucketPath = imageS3BucketPath;
        this.carId = carId;
    }
}
