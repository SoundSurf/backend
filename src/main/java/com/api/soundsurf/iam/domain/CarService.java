package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.entity.Car;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;

    @Transactional
    public List<CarDto.Get.Response> getAllCars() {
        List<Car> cars = repository.findAll();
        return cars.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CarDto.Get.Response convertToDto(Car car) {
        return new CarDto.Get.Response(car.getId(), car.getImage(), car.getName(), car.getDescription());
    }
}
