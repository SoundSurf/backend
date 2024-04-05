package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;

    public List<Car> getAllCars() {
        return repository.findAll();
    }

    public Car findByName(final String name) {
        return repository.findByName(name);
    }
}
