package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.entity.Car;
import com.api.soundsurf.iam.entity.User;
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
}
