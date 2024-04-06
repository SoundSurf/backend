package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.entity.Car;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.IllegalCarArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarBusinessService {
    private final CarService carService;
    private final UserService userService;

    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    public Car selectCar(final CarDto.Select.Request requestDto) {
        final User user = userService.findById(requestDto.getUserId());
        Car selectedCar = carService.findByName(requestDto.getCarName());
        user.setCar(selectedCar);
        userService.update(user);
        return selectedCar;
    }

    public Car cancelCar(final CarDto.Select.Request requestDto) {
        final User user = userService.findById(requestDto.getUserId());
        Car userCar = user.getCar();
        Car selectedCar = carService.findByName(requestDto.getCarName());

        if (userCar == null || !userCar.getName().equals(requestDto.getCarName())) {
            throw new IllegalCarArgumentException(userCar, selectedCar);
        }

        user.setCar(null);
        userService.update(user);
        return selectedCar;
    }
}
