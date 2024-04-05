package com.api.soundsurf.iam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private byte[] image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "car", orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    public Car(final byte[] image,final String name,final String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public static Car newInstance(final byte[] image,final String name,final String description) {
        return new Car(image, name, description);
    }
}
