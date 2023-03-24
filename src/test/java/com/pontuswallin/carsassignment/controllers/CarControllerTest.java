package com.pontuswallin.carsassignment.controllers;

import com.pontuswallin.carsassignment.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.generateSingleTestCarDTO;
import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.generateTestCarDTOs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @Mock
    CarService carService;

    CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carController = new CarController(carService);
    }

    @Test
    void getAllCars_shouldReturnEmptyCarListIfNoCarsArePresent() {
        var actual = carController.getAllCars();
        assertNotNull(actual);
        verify(carService, times(1)).getAllCars();
    }

    @Test
    void getAllCars_shouldReturnCarsIfCarsArePresent() {
        var expected = generateTestCarDTOs();
        when(carService.getAllCars()).thenReturn(generateTestCarDTOs());

        var actual = carController.getAllCars();
        assertNotNull(actual);
        assertIterableEquals(expected, actual);

        verify(carService, times(1)).getAllCars();
    }

    @Test
    void getCarById_shouldReturnSingleCarIfCarIsPresent() {
        var expected = generateSingleTestCarDTO();
        when(carService.getCarById(1L)).thenReturn(generateSingleTestCarDTO());

        var actual = carController.getCarById(1L);
        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(carService, times(1)).getCarById(1L);
    }

    @Test
    void getCarById_shouldReturnNNullIfCarIsNotPresent() {
        when(carService.getCarById(1L)).thenReturn(null);

        var actual = carController.getCarById(1L);
        assertNull(actual);

        verify(carService, times(1)).getCarById(1L);
    }
}