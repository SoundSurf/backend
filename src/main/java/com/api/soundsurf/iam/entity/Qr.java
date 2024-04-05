package com.api.soundsurf.iam.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "qrs")
public class Qr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private byte[] qr;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Qr(byte[] qr, User user) {
        this.qr = qr;
        this.user = user;
    }

public static Qr newInstance(final byte[] qr, final User user) {
        return new Qr(qr, user);
    }
}