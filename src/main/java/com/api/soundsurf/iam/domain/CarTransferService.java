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
public class CarTransferService {
    private final CarBusinessService businessService;

    @Transactional
    public List<CarDto.GetAll.Response> getAllCars() {
        List<Car> cars = businessService.getAllCars();
        return cars.stream().map(car -> new CarDto.GetAll.Response(car.getId(), car.getImage(), car.getName(), car.getDescription())).collect(Collectors.toList());
    }
}
