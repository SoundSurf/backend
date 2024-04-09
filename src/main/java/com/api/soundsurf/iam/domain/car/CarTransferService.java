package com.api.soundsurf.iam.domain.car;

import com.api.soundsurf.iam.domain.user.UserBusinessService;
import com.api.soundsurf.iam.dto.CarDto;
import com.api.soundsurf.iam.dto.SessionUser;
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
    private final UserBusinessService userBusinessService;

    @Transactional
    public List<CarDto.GetAll.Response> getAllCars() {
        List<Car> cars = businessService.getAllCars();
        return cars.stream().map(car -> new CarDto.GetAll.Response(car.getId(), car.getImage(), car.getName(), car.getDescription())).collect(Collectors.toList());
    }

    @Transactional
    public void selectCar(final SessionUser sessionUser, final CarDto.Select.Request requestDto) {
        final var car = businessService.getCar(requestDto.getId());

        userBusinessService.setCar(sessionUser.getUserId(), car);
    }

    @Transactional
    public void cancelCar(final SessionUser sessionUser) {
        userBusinessService.setCar(sessionUser.getUserId(), null);
    }
}
