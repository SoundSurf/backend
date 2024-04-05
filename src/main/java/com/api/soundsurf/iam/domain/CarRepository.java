package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAll();
    Car findByName(final String name);
}
