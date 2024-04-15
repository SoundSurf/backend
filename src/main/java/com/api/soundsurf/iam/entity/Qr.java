package com.api.soundsurf.iam.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "qrs")
public class Qr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private byte[] qr;

    @JsonManagedReference
    @OneToOne(mappedBy = "qr", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private User user;

    public Qr(final byte[] qr) {
        this.qr = qr;
    }

    public static Qr newInstance(final byte[] qr) {
        return new Qr(qr);
    }
}