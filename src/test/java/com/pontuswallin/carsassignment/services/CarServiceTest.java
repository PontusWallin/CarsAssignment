package com.pontuswallin.carsassignment.services;

import com.pontuswallin.carsassignment.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.generateSingleTestCarDTO;
import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.generateTestCarDTOs;
import static com.pontuswallin.carsassignment.helpers.TestModelsBuilder.generateSingleTestCarModel;
import static com.pontuswallin.carsassignment.helpers.TestModelsBuilder.generateTestCarModelList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    CarRepository carRepository;

    ModelMapper mapper = new ModelMapper();

    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carService = new CarService(carRepository, mapper);
    }

    @Test
    void getAllCars_shouldReturnCarsIfCarsArePresent() {
        var expected = generateTestCarDTOs();
        when(carRepository.findAll()).thenReturn(generateTestCarModelList());
        var actual = carService.getAllCars();

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getAllCars_shouldReturnEmptyListIfNoCarsArePresent() {
        when(carRepository.findAll()).thenReturn(List.of());
        var actual = carService.getAllCars();

        assertNotNull(actual);
        assertEquals(0, actual.size());

        verify(carRepository, times(1)).findAll();
    }
    @Test
    void getCarById_shouldReturnSingleCarIfCarIsPresent() {
        var expected = generateSingleTestCarDTO();
        when(carRepository.findById(1L))
                .thenReturn(Optional.ofNullable(generateSingleTestCarModel()));
        var actual = carService.getCarById(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void getCarById_shouldReturnNullIfNoCarIsPresent() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());
        var actual = carService.getCarById(1L);

        assertNull(actual);

        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void getCarsByUserId_shouldReturnCarsIfCarsIsPresent() {
        var expected = generateTestCarDTOs();
        when(carRepository.findByUserId(1L))
                .thenReturn(generateTestCarModelList());
        var actual = carService.getCarsByUserId(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(carRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getCarsByUserId_shouldReturnEmptyListIfCarsAreNotPresent() {
        when(carRepository.findByUserId(1L)).thenReturn(List.of());
        var actual = carService.getCarsByUserId(1L);

        assertEquals(0, actual.size());

        verify(carRepository, times(1)).findByUserId(1L);
    }
}