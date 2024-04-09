package com.api.soundsurf.iam.domain.car;

import com.api.soundsurf.iam.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarBusinessService {
    private final CarService carService;

    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    public Car getCar(final Long id) {
        return carService.findById(id);
    }

}
