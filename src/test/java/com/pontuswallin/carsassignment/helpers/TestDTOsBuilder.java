package com.pontuswallin.carsassignment.helpers;

import com.pontuswallin.carsassignment.dto.CarDTO;
import com.pontuswallin.carsassignment.dto.UserCarsDTO;
import com.pontuswallin.carsassignment.dto.UserDTO;

import java.util.List;

public class TestDTOsBuilder {
    public static CarDTO generateSingleTestCarDTO() {
        return buildFirstTestCar();
    }

    public static List<CarDTO> generateTestCarDTOs() {
        return List.of(
                buildFirstTestCar(),
                buildSecondTestCar()
        );
    }

    public static UserDTO generateSingleTestUserDTO() {
        return buildFirstTestUser();
    }

    public static List<UserDTO> generateTestUserDTOList() {
        return List.of(
                buildFirstTestUser(),
                buildSecondTestUser()
        );
    }

    public static UserCarsDTO generateTestUserCarsDTO() {
        return UserCarsDTO.builder()
                .user(buildFirstTestUser())
                .cars(List.of(
                        buildFirstTestCar(),
                        buildSecondTestCar()
                ))
                .build();
    }
    public static UserCarsDTO generateTestUserCarsDTOWithEmptyCarList() {
        return UserCarsDTO.builder()
                .user(generateSingleTestUserDTO())
                .cars(List.of())
                .build();
    }

    private static UserDTO buildSecondTestUser() {
        return UserDTO.builder()
                .id(2L)
                .name("John Smith")
                .build();
    }

    private static UserDTO buildFirstTestUser() {
        return UserDTO.builder()
                .id(1L)
                .name("Test Testson")
                .build();
    }

    private static CarDTO buildFirstTestCar() {
        return CarDTO.builder()
                .id(1L)
                .make("Volvo")
                .model("V70")
                .numberplate("TEST123")
                .build();
    }

    private static CarDTO buildSecondTestCar() {
        return CarDTO.builder()
                .id(2L)
                .make("Lada")
                .model("Niva")
                .numberplate("123TEST")
                .build();
    }
}
