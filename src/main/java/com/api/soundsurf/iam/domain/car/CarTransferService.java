package com.api.soundsurf.iam.domain.car;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.entity.Car;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarTransferService {
    private final CarBusinessService businessService;
    private final UserBusinessService userBusinessService;

    @Transactional
    public CarDto.GetAll.Response getAllCars() {
        List<Car> cars = businessService.getAllCars();

        return new CarDto.GetAll.Response(
                cars.stream()
                        .map(CarDto.Car::new)
                        .toList()
        );
    }

    @Transactional
    public void selectCar(final SessionUser sessionUser, final CarDto.Select.Request requestDto) {
        final var car = businessService.getCar(requestDto.getId());

        userBusinessService.setCar(sessionUser.getUserId(), car);
    }

    public Car getDefaultCar() {
        return businessService.getCar(1L);
    }

    public CarDto.GetUserCar.Response  getUserCar(final SessionUser sessionUser) {
        final var car = userBusinessService.getUserCar(sessionUser.getUserId());

        return new CarDto.GetUserCar.Response(new CarDto.Car(car));
    }
}
