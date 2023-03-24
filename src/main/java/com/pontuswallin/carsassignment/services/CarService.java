package com.pontuswallin.carsassignment.services;

import com.pontuswallin.carsassignment.domain.Car;
import com.pontuswallin.carsassignment.dto.CarDTO;
import com.pontuswallin.carsassignment.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;
    public CarService(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    public List<CarDTO> getAllCars() {
        Iterable<Car> allCars = carRepository.findAll();
        return StreamSupport.stream(allCars.spliterator(), false)
                .map(car -> mapper.map(car, CarDTO.class))
                .collect(Collectors.toList());
    }

    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id).orElse(null);

        if(car == null) {
            return null;
        }

        return mapper.map(car, CarDTO.class);
    }

    public List<CarDTO> getCarsByUserId(Long userId) {
        List<Car> byUserId = carRepository.findByUserId(userId);
        return byUserId.stream()
                .map(car -> mapper.map(car, CarDTO.class))
                .collect(Collectors.toList());
    }
}
