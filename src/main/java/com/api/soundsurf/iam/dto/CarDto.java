package com.api.soundsurf.iam.dto;

import com.api.soundsurf.iam.entity.Car;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class CarDto {
    @Schema(name = "CarDto.Car")
    @Getter
    public static class Car {
        private final Long id;
        private final byte[] image;
        private final String name;
        private final String description;

        public Car(com.api.soundsurf.iam.entity.Car entityCar) {
            this.id = entityCar.getId();
            this.image = entityCar.getImage();
            this.name = entityCar.getName();
            this.description = entityCar.getDescription();
        }
    }

    public static class GetAll {
        @AllArgsConstructor
        @Schema(name = "CarDto.Get.Response")
        @Getter
        public static class Response {
            private List<Car> cars;
        }
    }

    public static class Select {
        @Getter
        @Schema(name = "CarDto.Select.Request")
        public static class Request {
            @NotNull
            private Long id;
        }

    }

    public static class GetUserCar {
        @Getter
        @Schema(name = "GenreDto.GetUserCar.Response")
        @AllArgsConstructor
        public static class Response {
            private Car car;
        }
    }
}
