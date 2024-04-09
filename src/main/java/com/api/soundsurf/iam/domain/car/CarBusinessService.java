package com.api.soundsurf.iam.domain.car;

import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.entity.Car;
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

    public Car getCar(final Long id) {
        return carService.findById(id);
    }

    public Car cancelCar(final Long userId,  final CarDto.Select.Request requestDto) {
        final var user = userService.findById(userId);
        final var  userCar = user.getCar();
        final var selectedCar = carService.findByName(requestDto.getCarName());

        if (userCar == null || !userCar.getName().equals(requestDto.getCarName())) {
            throw new IllegalCarArgumentException(userCar, selectedCar);
        }

        user.setCar(null);
        userService.update(user);
        return selectedCar;
    }
}
