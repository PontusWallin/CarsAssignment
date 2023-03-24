package com.pontuswallin.carsassignment.helpers;

import com.pontuswallin.carsassignment.domain.Car;
import com.pontuswallin.carsassignment.domain.User;


import java.util.List;

public class TestModelsBuilder {

    public static User generateSingleTestUserModel() {
        return User.builder()
                .id(1L)
                .name("Test Testson")
                .build();
    }
    public static List<User> generateTestUserModelList() {
        return List.of(
                buildFirstTestUser(),
                buildSecondTestUser()
        );
    }

    public static Car generateSingleTestCarModel() {
        return buildfirstTestCar();
    }
    public static List<Car> generateTestCarModelList() {
        return List.of(
                buildfirstTestCar(),
                buildSecondTestCar()
        );
    }

    private static Car buildSecondTestCar() {
        return Car.builder()
                .id(2L)
                .make("Lada")
                .model("Niva")
                .numberplate("123TEST")
                .build();
    }

    private static Car buildfirstTestCar() {
        return Car.builder()
                .id(1L)
                .make("Volvo")
                .model("V70")
                .numberplate("TEST123")
                .build();
    }

    private static User buildFirstTestUser() {
        return User.builder()
                .id(1L)
                .name("Test Testson")
                .build();
    }
    private static User buildSecondTestUser() {
        return User.builder()
                .id(2L)
                .name("John Smith")
                .build();
    }
}
