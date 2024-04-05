package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarBusinessService {
    private final CarService service;

    public List<Car> getAllCars() {
        return service.getAllCars();
    }
}
