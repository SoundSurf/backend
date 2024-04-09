package com.api.soundsurf.iam.domain.car;

import com.api.soundsurf.iam.entity.Car;
import com.api.soundsurf.iam.exception.IllegalCarArgumentException;
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

    public Car findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalCarArgumentException(id));
    }
}
