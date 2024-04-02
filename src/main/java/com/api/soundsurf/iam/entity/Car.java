package com.api.soundsurf.iam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private byte[] image;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    public Car(byte[] image, String name, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public static final Car newInstance(byte[] image, String name, String description) {
        return new Car(image, name, description);
    }
}
